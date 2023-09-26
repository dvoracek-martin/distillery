package com.dvoracek.distillery.distillation.plan.service.internal;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.service.DistillationPhaseService;
import com.dvoracek.distillery.distillation.phase.service.internal.CreateDistillationPhaseDto;
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

import static java.util.Comparator.comparing;

@Service
@Transactional
public class DefaultDistillationPlanService implements DistillationPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPlanService.class);
    private final DistillationPhaseService distillationPhaseService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DistillationPlanRepository distillationPlanRepository;

    public DefaultDistillationPlanService(DistillationPhaseService distillationPhaseService, KafkaTemplate<String, String> kafkaTemplate, DistillationPlanRepository distillationPlanRepository) {
        this.distillationPhaseService = distillationPhaseService;
        this.kafkaTemplate = kafkaTemplate;
        this.distillationPlanRepository = distillationPlanRepository;
    }

    public List<DistillationPlan> getAll() {
        List<DistillationPlan> distillationPlans = distillationPlanRepository.findAll();
        for (DistillationPlan distillationPlan : distillationPlans) {
            distillationPlan.getDistillationPhases().sort(comparing(DistillationPhase::getId));
        }
        distillationPlans.sort(comparing(DistillationPlan::getId));
        return distillationPlans;
    }

    public DistillationPlan createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto) {
        DistillationPlan distillationPlan = new DistillationPlan();
        distillationPlan.setName(createDistillationPlanDto.getName());
        distillationPlan.setDescription(createDistillationPlanDto.getDescription());
        List<DistillationPhase> distillationPhases = new ArrayList<>();
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
        return distillationPlan;
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
    public DistillationPlan editPlan(Long id, EditDistillationPlanDto editDistillationPlanDto) {
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
        return distillationPlan;
    }

    private List<DistillationPhase> editDistillationPhases(DistillationPlan distillationPlan, List<EditDistillationPhaseDto> distillationPhasesFromDto) {
        return distillationPhasesFromDto
                .stream()
                .map(it -> new
                        DistillationPhase(it.getId(), it.getName(), distillationPlan, it.getTemperature(), it.getFlow(), it.getTime())
                ).toList();
    }

    @Override
    public DistillationPlan getDistillationPlan(Long id) {
        return distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id));
    }

    @Override
    public void startDistillation(DistillationPlan distillationPlan) {
        kafkaTemplate.send("distillation-started", Long.toString(distillationPlan.getId()));
        LOGGER.info("Distillation started. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
    }

    @Override
    public void terminateDistillation(DistillationPlan distillationPlan) {
        kafkaTemplate.send("distillation-terminated", Long.toString(distillationPlan.getId()));
        LOGGER.info("Distillation terminated. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
    }
    @Override
    public void terminateDistillationByUser(DistillationPlan distillationPlan) {
        kafkaTemplate.send("distillation-terminated-by-user", Long.toString(distillationPlan.getId()));
        LOGGER.info("Distillation terminated by user. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
    }

    @Override
    public void jumpToNextPhase(DistillationPlan distillationPlan) {
        kafkaTemplate.send("distillation-next-phase", Long.toString(distillationPlan.getId()));
        LOGGER.info("Distillation ID: {}, name: {} jumped to the next phase", distillationPlan.getId(), distillationPlan.getName());
    }
}
