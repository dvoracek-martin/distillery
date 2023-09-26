package com.dvoracek.distillery.distillation.process.service.internal;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.service.internal.DistillationPhaseDto;
import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import com.dvoracek.distillery.distillation.plan.repository.DistillationPlanRepository;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanNotFoundException;
import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;
import com.dvoracek.distillery.distillation.procedure.service.DistillationProcedureService;
import com.dvoracek.distillery.distillation.process.service.DistillationProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Comparator.comparing;

@Service
public class DefaultDistillationProcessService implements DistillationProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationProcessService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DistillationPlanRepository distillationPlanRepository;
    private final DistillationProcedureService distillationProcedureService;
    private long timeStartedInMillis;
    private Double alcLevel;
    private boolean isTerminated;
    private boolean isEnergyOn;
    private boolean isPaused;
    private DistillationProcessDataFromRaspiDto distillationProcessDataFromRaspiDto;
    private DistillationPhase currentDistillationPhase;
    private DistillationPlan currentDistillationPlan;
    private boolean isDistillationPlanDirty;
    private long timeElapsedInMillis;
    private long timeElapsedSinceStartInMillis;
    private DistillationProcedure distillationProcedure;

    public DefaultDistillationProcessService(KafkaTemplate<String, String> kafkaTemplate, DistillationPlanRepository distillationPlanRepository, DistillationProcedureService distillationProcedureService) {
        this.kafkaTemplate = kafkaTemplate;
        this.distillationPlanRepository = distillationPlanRepository;
        this.distillationProcedureService = distillationProcedureService;
    }

    @Override
    public void startDistillation(long planId) {
        init();
        currentDistillationPlan = distillationPlanRepository.findById(planId).orElseThrow(() -> new DistillationPlanNotFoundException(planId));
        currentDistillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
        isPaused = false;
        isEnergyOn = true;
        distillationProcedure = distillationProcedureService.createDistillationProcedure(planId);
        timeElapsedSinceStartInMillis = System.currentTimeMillis();

        startLoop(currentDistillationPlan);
    }

    @Override
    public void updateDistillationProcessData(DistillationProcessDataFromRaspiDto distillationProcessDataFromRaspiDto) {
        this.distillationProcessDataFromRaspiDto = distillationProcessDataFromRaspiDto;
        alcLevel = (this.distillationProcessDataFromRaspiDto.getWeight() - this.distillationProcessDataFromRaspiDto.getFlow()) / (this.distillationProcessDataFromRaspiDto.getFlow() * 0.79 - this.distillationProcessDataFromRaspiDto.getFlow());
    }

    @Override
    public void nextPhase() {
        if (this.currentDistillationPlan != null) {
            Long planId = this.currentDistillationPlan.getId();
            currentDistillationPlan = distillationPlanRepository.findById(planId).orElseThrow(() -> new DistillationPlanNotFoundException(planId));
            currentDistillationPhase = currentDistillationPlan.getDistillationPhases().stream().filter(phase -> currentDistillationPhase.getId().equals(phase.getId())).findAny().orElse(null);
            currentDistillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
            int indexOfCurrentPhase = currentDistillationPlan.getDistillationPhases().indexOf(currentDistillationPhase);
            if (indexOfCurrentPhase == currentDistillationPlan.getDistillationPhases().size() - 1) {
                kafkaTemplate.send("distillation-terminated", Long.toString(planId));
            } else {
                timeStartedInMillis = System.currentTimeMillis();
                currentDistillationPhase = currentDistillationPlan.getDistillationPhases().get(++indexOfCurrentPhase);
            }
        }
    }

    @Override
    public void terminateProcess(long planId) {
        if (!isTerminated) {
            isTerminated = true;
            isEnergyOn = false;
            timeElapsedInMillis = 0;
            timeStartedInMillis = 0;
            currentDistillationPlan = null;
            distillationProcedureService.terminateDistillationProcedure(distillationProcedure.getId());
        }
    }

    @Override
    public void terminateProcessByUser(long planId) {
        if (!isTerminated) {
            isTerminated = true;
            isEnergyOn = false;
            timeElapsedInMillis = 0;
            timeStartedInMillis = 0;
            currentDistillationPlan = null;
            distillationProcedureService.terminateDistillationProcedureByUser(distillationProcedure.getId());
        }
    }

    @Override
    public DistillationProcessDataToFrontendDto getDataForFrontend() {
        if (currentDistillationPlan == null) {
            DistillationProcessDataToFrontendDto distillationProcessDataToFrontendDto = new DistillationProcessDataToFrontendDto();
            distillationProcessDataToFrontendDto.setTerminated(true);
            return distillationProcessDataToFrontendDto;
        }
        return new DistillationProcessDataToFrontendDto(DistillationPlanDto.toDistillationPlanDto(currentDistillationPlan), DistillationPhaseDto.toDistillationPhaseDto(currentDistillationPhase), distillationProcessDataFromRaspiDto.getTemperature(), distillationProcessDataFromRaspiDto.getFlow(), distillationProcessDataFromRaspiDto.getWeight(), timeElapsedInMillis, isTerminated, isPaused, isEnergyOn, alcLevel);
    }

    @Override
    public void shallReloadDistillationPlan(boolean isDistillationPlanDirty) {
        this.isDistillationPlanDirty = isDistillationPlanDirty;
    }

    @Override
    public void logBackendProgress(String data) {
        LOGGER.info(data);
    }

    private void startLoop(DistillationPlan distillationPlan) {
        currentDistillationPhase = distillationPlan.getDistillationPhases().get(0);
        isTerminated = false;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (isTerminated) {
                executor.shutdown();
            }
            if (isDistillationPlanDirty) {
                currentDistillationPlan = distillationPlanRepository.findById(currentDistillationPlan.getId()).orElseThrow(() -> new DistillationPlanNotFoundException(currentDistillationPlan.getId()));
                currentDistillationPhase = currentDistillationPlan.getDistillationPhases().stream().filter(phase -> currentDistillationPhase.getId().equals(phase.getId())).findAny().orElse(null);
                currentDistillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
                isDistillationPlanDirty = false;
            }
            timeElapsedInMillis = System.currentTimeMillis() - timeStartedInMillis;
            timeElapsedSinceStartInMillis = System.currentTimeMillis() - timeElapsedSinceStartInMillis;
            DistillationProcessDataToRaspiDto distillationProcessDataToRaspiDto = new DistillationProcessDataToRaspiDto();
            distillationProcessDataToRaspiDto.setDistillationProcedureId(distillationProcedure.getId());
            distillationProcessDataToRaspiDto.setDistillationPhaseId(currentDistillationPhase.getId());
            distillationProcessDataToRaspiDto.setTimeStartedInMillis(timeElapsedInMillis);
            distillationProcessDataToRaspiDto.setTimeElapsedSinceStartInMillis(timeElapsedSinceStartInMillis);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                kafkaTemplate.send("distillation-progress-backend", objectMapper.writeValueAsString(distillationProcessDataToRaspiDto));
            } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage());
            }
            if (distillationProcessDataFromRaspiDto != null) {
                if (currentDistillationPhase.getTime() - timeElapsedInMillis < 0) {
                    kafkaTemplate.send("distillation-next-phase", Long.toString(distillationPlan.getId()));
                }
                if (distillationProcessDataFromRaspiDto.getTemperature() >= currentDistillationPhase.getTemperature() || distillationProcessDataFromRaspiDto.getFlow() >= currentDistillationPhase.getFlow()) {
                    isEnergyOn = false;
                    isPaused = true;
                    kafkaTemplate.send("distillation-paused", Long.toString(distillationPlan.getId()));
                } else {
                    isEnergyOn = true;
                    isPaused = false;
                    kafkaTemplate.send("distillation-continued", Long.toString(distillationPlan.getId()));
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void init() {
        timeStartedInMillis = System.currentTimeMillis();

    }
}
