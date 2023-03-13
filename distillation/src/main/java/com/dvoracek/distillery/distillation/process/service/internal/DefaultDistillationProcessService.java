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

    private DistillationProcedure distillationProcedure;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DistillationPlanRepository distillationPlanRepository;
    private final DistillationProcedureService distillationProcedureService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationProcessService.class);

    public DefaultDistillationProcessService(KafkaTemplate<String, String> kafkaTemplate, DistillationPlanRepository distillationPlanRepository, DistillationProcedureService distillationProcedureService) {
        this.kafkaTemplate = kafkaTemplate;
        this.distillationPlanRepository = distillationPlanRepository;
        this.distillationProcedureService = distillationProcedureService;
    }

    @Override
    public void startDistillation(long planId) {
        init();
        try {
            currentDistillationPlan = distillationPlanRepository.findById(planId).orElseThrow(() -> new DistillationPlanNotFoundException(planId));
            this.currentDistillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
            this.isPaused = false;
            this.isEnergyOn = true;
            distillationProcedure = distillationProcedureService.createDistillationProcedure(planId);
            startLoop(currentDistillationPlan);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDistillationProcessData(DistillationProcessDataFromRaspiDto distillationProcessDataFromRaspiDto) {
        this.distillationProcessDataFromRaspiDto = distillationProcessDataFromRaspiDto;
        this.alcLevel = ((this.distillationProcessDataFromRaspiDto.getWeight() - this.distillationProcessDataFromRaspiDto.getFlow())) / (this.distillationProcessDataFromRaspiDto.getFlow() * 0.79 - this.distillationProcessDataFromRaspiDto.getFlow());
    }

    @Override
    public void nextPhase() {
        if (this.currentDistillationPlan != null) {
            Long planId = this.currentDistillationPlan.getId();
            this.currentDistillationPlan = distillationPlanRepository.findById(planId).orElseThrow(() -> new DistillationPlanNotFoundException(planId));
            this.currentDistillationPhase = currentDistillationPlan.getDistillationPhases().stream().filter(phase -> this.currentDistillationPhase.getId().equals(phase.getId())).findAny().orElse(null);
            this.currentDistillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
            int indexOfCurrentPhase = currentDistillationPlan.getDistillationPhases().indexOf(this.currentDistillationPhase);
            if (indexOfCurrentPhase == currentDistillationPlan.getDistillationPhases().size() - 1) {
                this.kafkaTemplate.send("distillation-terminated", Long.toString(planId));
            } else {
                this.timeStartedInMillis = System.currentTimeMillis();
                this.currentDistillationPhase = currentDistillationPlan.getDistillationPhases().get(++indexOfCurrentPhase);
            }
        }
    }

    @Override
    public void terminateProcess(long planId) {
        if (!isTerminated) {
            this.isTerminated = true;
            this.isEnergyOn = false;
            this.timeElapsedInMillis = 0;
            this.timeStartedInMillis = 0;
            this.currentDistillationPlan = null;
            distillationProcedureService.terminateDistillationProcedure(distillationProcedure.getPlanId());
        }
    }

    @Override
    public DistillationProcessDataToFrontendDto getDataForFrontend() {
        if (this.currentDistillationPlan == null) {
            DistillationProcessDataToFrontendDto distillationProcessDataToFrontendDto = new DistillationProcessDataToFrontendDto();
            distillationProcessDataToFrontendDto.setTerminated(true);
            return distillationProcessDataToFrontendDto;
        }
        return new DistillationProcessDataToFrontendDto(DistillationPlanDto.toDistillationPlanDto(this.currentDistillationPlan), DistillationPhaseDto.toDistillationPhaseDto(this.currentDistillationPhase), this.distillationProcessDataFromRaspiDto.getTemperature(), this.distillationProcessDataFromRaspiDto.getFlow(), this.distillationProcessDataFromRaspiDto.getWeight(), this.timeElapsedInMillis, this.isTerminated, this.isPaused, this.isEnergyOn, this.alcLevel);
    }

    @Override
    public void shallReloadDistillationPlan(boolean isDistillationPlanDirty) {
        this.isDistillationPlanDirty = isDistillationPlanDirty;
    }

    private void startLoop(DistillationPlan distillationPlan) throws JsonProcessingException {
        this.currentDistillationPhase = distillationPlan.getDistillationPhases().get(0);
        isTerminated = false;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (isTerminated) {
                executor.shutdown();
            }
            if (this.isDistillationPlanDirty) {
                this.currentDistillationPlan = distillationPlanRepository.findById(this.currentDistillationPlan.getId()).orElseThrow(() -> new DistillationPlanNotFoundException(this.currentDistillationPlan.getId()));
                this.currentDistillationPhase = currentDistillationPlan.getDistillationPhases().stream().filter(phase -> this.currentDistillationPhase.getId().equals(phase.getId())).findAny().orElse(null);
                this.currentDistillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
                this.isDistillationPlanDirty = false;
            }
            this.timeElapsedInMillis = System.currentTimeMillis() - timeStartedInMillis;
            DistillationProcessDataToRaspiDto distillationProcessDataToRaspiDto = new DistillationProcessDataToRaspiDto();
            distillationProcessDataToRaspiDto.setDistillationProcedureId(distillationProcedure.getId());
            distillationProcessDataToRaspiDto.setTimeStartedInMillis(timeElapsedInMillis);
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
                    this.isEnergyOn = false;
                    this.isPaused = true;
                    kafkaTemplate.send("distillation-paused", Long.toString(distillationPlan.getId()));
                } else {
                    this.isEnergyOn = true;
                    this.isPaused = false;
                    kafkaTemplate.send("distillation-continued", Long.toString(distillationPlan.getId()));
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void init() {
        timeStartedInMillis = System.currentTimeMillis();

    }
}
