package com.dvoracek.distillery.distillation.plan.service.internal;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.service.DistillationPhaseService;
import com.dvoracek.distillery.distillation.phase.service.internal.CreateDistillationPhaseDto;
import com.dvoracek.distillery.distillation.phase.service.internal.DistillationPhaseDto;
import com.dvoracek.distillery.distillation.phase.service.internal.EditDistillationPhaseDto;
import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import com.dvoracek.distillery.distillation.plan.repository.DistillationPlanRepository;
import com.dvoracek.distillery.distillation.plan.service.DistillationPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class DefaultDistillationPlanService implements DistillationPlanService {

    private final DistillationPhaseService distillationPhaseService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPlanService.class);

    private final DistillationPlanRepository distillationPlanRepository;

    public DefaultDistillationPlanService(DistillationPhaseService distillationPhaseService, KafkaTemplate<String, String> kafkaTemplate, DistillationPlanRepository distillationPlanRepository) {
        this.distillationPhaseService = distillationPhaseService;
        this.kafkaTemplate = kafkaTemplate;
        this.distillationPlanRepository = distillationPlanRepository;
    }

    public List<DistillationPlanDto> getAll() {
        List<DistillationPlanDto> distillationPlanDtos = distillationPlanRepository.findAll().stream().map(DistillationPlanDto::toDistillationPlanDto).collect(Collectors.toList());
        for (DistillationPlanDto distillationPlanDto : distillationPlanDtos) {
            distillationPlanDto.getDistillationPhases().sort(comparing(DistillationPhaseDto::getId));
        }
        distillationPlanDtos.sort(comparing(DistillationPlanDto::getId));
        return distillationPlanDtos;
    }

    public DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto) {
        DistillationPlan distillationPlan = new DistillationPlan();
        distillationPlan.setName(createDistillationPlanDto.getName());
        distillationPlan.setDescription(createDistillationPlanDto.getDescription());
        List<DistillationPhase>distillationPhases = new ArrayList<>();
        if (!createDistillationPlanDto.getDistillationPhases().isEmpty()) {
            for (CreateDistillationPhaseDto createDistillationPhaseDto : createDistillationPlanDto.getDistillationPhases()) {
                DistillationPhase distillationPhase = distillationPhaseService.createDistillationPhase(createDistillationPhaseDto);
                distillationPhase.setPlan(distillationPlan);
                distillationPhases.add(distillationPhase);
            }
        }
        distillationPlan.setDistillationPhases(distillationPhases);
        distillationPlan = distillationPlanRepository.saveAndFlush(distillationPlan);
        kafkaTemplate.send("distillation-plan-created", Long.toString(distillationPlan.getId()));
        LOGGER.info("Plan created. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
        return DistillationPlanDto.toDistillationPlanDto(distillationPlan);
    }

    @Override
    public void deleteDistillationPlan(Long id) {
        DistillationPlan distillationPlan = distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id));
        distillationPlanRepository.delete(distillationPlan);
        distillationPlan.getDistillationPhases()
                .forEach(phase -> kafkaTemplate.send("distillation-phase-deleted", Long.toString(phase.getId())));
        kafkaTemplate.send("distillation-plan-deleted", Long.toString(id));
        LOGGER.info("Plan deleted. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
    }

    @Override
    public DistillationPlanDto editPlan(Long id, EditDistillationPlanDto editDistillationPlanDto) {
        DistillationPlan distillationPlan = distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id));
        distillationPlan.setName(editDistillationPlanDto.getName());
        distillationPlan.setDescription(editDistillationPlanDto.getDescription());
        List<DistillationPhase> distillationPhases = editDistillationPhases(distillationPlan, editDistillationPlanDto.getDistillationPhases());
        distillationPlan.getDistillationPhases().clear();
        distillationPlan.getDistillationPhases().addAll(distillationPhases);
        distillationPlanRepository.saveAndFlush(distillationPlan);
        LOGGER.info("Plan edit. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
        distillationPlan.getDistillationPhases()
                .forEach(phase -> {
                    LOGGER.info("Phase edit. ID: {}, name: {}", phase.getId(), phase.getName());
                    kafkaTemplate.send("distillation-phase-edited", Long.toString(phase.getId()));
                });
        kafkaTemplate.send("distillation-plan-edited", Long.toString(id));
        return DistillationPlanDto.toDistillationPlanDto(distillationPlan);
    }

    private List<DistillationPhase> editDistillationPhases(DistillationPlan distillationPlan, List<EditDistillationPhaseDto> distillationPhasesFromDto) {
        return distillationPhasesFromDto
                .stream()
                .map(it -> new
                        DistillationPhase(it.getId(), it.getName(), distillationPlan, it.getTemperature(), it.getFlow(), it.getTime())
                ).collect(Collectors.toList());
    }

    @Override
    public DistillationPlanDto getDistillationPlan(Long id) {
        return DistillationPlanDto.toDistillationPlanDto((distillationPlanRepository.findById(id)).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    @Override
    public void startDistillation(DistillationPlanDto distillationPlanDto) {
        kafkaTemplate.send("distillation-started", Long.toString(distillationPlanDto.getId()));
        LOGGER.info("Distillation started. ID: {}, name: {}", distillationPlanDto.getId(), distillationPlanDto.getName());
    }

    @Override
    public void terminateDistillation(DistillationPlanDto distillationPlanDto) {
        kafkaTemplate.send("distillation-terminated", Long.toString(distillationPlanDto.getId()));
        LOGGER.info("Distillation terminated. ID: {}, name: {}", distillationPlanDto.getId(), distillationPlanDto.getName());
    }

    @Override
    public void jumpToNextPhase(DistillationPlanDto distillationPlanDto) {
        kafkaTemplate.send("distillation-next-phase", Long.toString(distillationPlanDto.getId()));
        LOGGER.info("Distillation ID: {}, name: {} jumped to the next phase", distillationPlanDto.getId(), distillationPlanDto.getName());
    }
}
