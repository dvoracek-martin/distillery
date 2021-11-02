package com.dvoracek.distillery.service.distillation.plan.internal;

import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeDataRepository;
import com.dvoracek.distillery.domain.phase.DistillationPhase;
import com.dvoracek.distillery.domain.phase.DistillationPhaseRepository;
import com.dvoracek.distillery.domain.plan.DistillationPlan;
import com.dvoracek.distillery.domain.plan.DistillationPlanRepository;
import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import com.dvoracek.distillery.service.distillation.phase.internal.CreateDistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseNotFoundException;
import com.dvoracek.distillery.service.distillation.phase.internal.UpdateDistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.plan.DistillationPlanService;
import com.dvoracek.distillery.service.distillation.plan.events.DistillationPlanEventPublisher;
import com.dvoracek.distillery.service.distillation.plan.events.DistillationPlanTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class DefaultDistillationPlanService implements DistillationPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPlanService.class);

    private final DistillationPlanRepository distillationPlanRepository;
    private final DistillationPhaseRepository distillationPhaseRepository;
    private final DistillationPlanEventPublisher distillationPlanEventPublisher;
    private final DistillationExchangeDataService distillationExchangeDataService;

    public DefaultDistillationPlanService(DistillationPlanRepository distillationPlanRepository, DistillationPhaseRepository distillationPhaseRepository, DistillationPlanEventPublisher distillationPlanEventPublisher, DistillationExchangeDataService distillationExchangeDataService) {
        this.distillationPlanRepository = distillationPlanRepository;
        this.distillationPhaseRepository = distillationPhaseRepository;
        this.distillationPlanEventPublisher = distillationPlanEventPublisher;
        this.distillationExchangeDataService = distillationExchangeDataService;
    }

    public List<DistillationPlanDto> getAll() {
        List<DistillationPlanDto> distillationPlanDtos = distillationPlanRepository.findAll().stream()
                .map(DistillationPlanDto::toDistillationPlanDto).collect(Collectors.toList());
        for (DistillationPlanDto distillationPlanDto : distillationPlanDtos) {
            Collections.sort(distillationPlanDto.getDistillationPhases(), comparing(DistillationPhaseDto::getId));
        }
        return distillationPlanDtos;
    }

    @Override
    public DistillationPlanDto updatePlan(Long id, UpdateDistillationPlanDto updateDistillationPlanDto) {
        DistillationPlan distillationPlan = findById(id);
        distillationPlan.setName(updateDistillationPlanDto.getName());
        distillationPlan.setDescription(updateDistillationPlanDto.getDescription());
        List<DistillationPhase> phases = new ArrayList<>();
        for (UpdateDistillationPhaseDto updateDistillationPhaseDto : updateDistillationPlanDto.getDistillationPhases()) {
            // create a phase if it doesn't exist yet
            if (updateDistillationPhaseDto.getId() == null) {
                createNewPhase(distillationPlan, updateDistillationPhaseDto.getName(), updateDistillationPhaseDto.getTemperature(), updateDistillationPhaseDto.getFlow(), updateDistillationPhaseDto.getTime());
            } else {
                DistillationPhase distillationPhase = distillationPhaseRepository.findById(updateDistillationPhaseDto.getId()).orElseThrow(() -> new DistillationPhaseNotFoundException(id));
                distillationPhase.setName(updateDistillationPhaseDto.getName());
                distillationPhase.setFlow(updateDistillationPhaseDto.getFlow());
                distillationPhase.setTemperature(updateDistillationPhaseDto.getTemperature());
                distillationPhase.setTime(updateDistillationPhaseDto.getTime());
                distillationPhase.setPlan(distillationPlan);
                phases.add(distillationPhase);

            }
        }
        // TODO fix not working cascade deletion of phases
        // filter distillation phases to remove
        for (DistillationPhase distillationPhase : distillationPlan.getDistillationPhases()) {
            boolean isPresent = false;
            for (DistillationPhase updatedPhase : phases) {
                if (updatedPhase.getId().equals(distillationPhase.getId())) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                distillationPhaseRepository.delete(distillationPhase);
            }
        }
        distillationPlan.setDistillationPhases(phases);
        LOGGER.info("Phase updated. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
        DistillationPlanDto distillationPlanDto = DistillationPlanDto.toDistillationPlanDto(distillationPlan);
        distillationPlanEventPublisher.publishDistillationPlanUpdatedEvent(distillationPlanDto);
        return distillationPlanDto;
    }

    public DistillationPlanDto getDistillationPlan(Long id) {
        return DistillationPlanDto.toDistillationPlanDto((distillationPlanRepository.findById(id)).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    public DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto) {
        DistillationPlan distillationPlan = new DistillationPlan();
        distillationPlan.setName(createDistillationPlanDto.getName());
        distillationPlan.setDescription(createDistillationPlanDto.getDescription());
        distillationPlan = distillationPlanRepository.save(distillationPlan);
        if (!createDistillationPlanDto.getDistillationPhases().isEmpty()) {
            for (CreateDistillationPhaseDto createDistillationPhaseDto : createDistillationPlanDto.getDistillationPhases()) {
                createNewPhase(distillationPlan, createDistillationPhaseDto.getName(), createDistillationPhaseDto.getTemperature(), createDistillationPhaseDto.getFlow(), createDistillationPhaseDto.getTime());
            }
        }
        return DistillationPlanDto.toDistillationPlanDto(distillationPlan);
    }

    @Override
    public void deleteDistillationPlan(Long id) {
        distillationPlanRepository.delete(distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    @Override
    public void startDistillation(DistillationPlanDto distillationPlanDto) {
        DistillationPlanTask distillationPlanTask = new DistillationPlanTask(distillationPlanEventPublisher, distillationExchangeDataService);
        distillationPlanTask.setDistillationPlanDto(distillationPlanDto);
        distillationPlanEventPublisher.publishDistillationPlanStartEvent(distillationPlanDto);
    }


    private DistillationPlan findById(Long id) {
        return distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id));
    }

    private void createNewPhase(DistillationPlan distillationPlan, String name, double temperature, double flow, Long time) {
        DistillationPhase distillationPhase = new DistillationPhase();
        distillationPhase.setPlan(distillationPlan);
        distillationPhase.setName(name);
        distillationPhase.setTemperature(temperature);
        distillationPhase.setFlow(flow);
        distillationPhase.setTime(time);
        distillationPhaseRepository.save(distillationPhase);
    }
}
