package com.dvoracek.distillery.service.distillation.plan.internal;

import com.dvoracek.distillery.domain.phase.DistillationPhase;
import com.dvoracek.distillery.domain.phase.DistillationPhaseRepository;
import com.dvoracek.distillery.domain.plan.DistillationPlan;
import com.dvoracek.distillery.domain.plan.DistillationPlanRepository;
import com.dvoracek.distillery.service.distillation.phase.DistillationPhaseNotFoundException;
import com.dvoracek.distillery.service.distillation.plan.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultDistillationPlanService implements DistillationPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPlanService.class);

    private final DistillationPlanRepository distillationPlanRepository;
    private final DistillationPhaseRepository distillationPhaseRepository;

    public DefaultDistillationPlanService(DistillationPlanRepository distillationPlanRepository, DistillationPhaseRepository distillationPhaseRepository) {
        this.distillationPlanRepository = distillationPlanRepository;
        this.distillationPhaseRepository = distillationPhaseRepository;
    }

    public List<DistillationPlanDto> getAll() {
        return distillationPlanRepository.findAll().stream()
                .map(DistillationPlanDto::toDistillationPlanDto).collect(Collectors.toList());
    }

    @Override
    public DistillationPlanDto updatePlan(Long id, UpdateDistillationPlanDto updateDistillationPlanDto) {
        DistillationPlan distillationPlan = findById(id);
        distillationPlan.setName(updateDistillationPlanDto.getName());
        distillationPlan.setDescription(updateDistillationPlanDto.getDescription());
        List<DistillationPhase> phases = new ArrayList<>();
        for (Long phaseId : updateDistillationPlanDto.getPhaseIds()) {
            this.distillationPhaseRepository.findById(phaseId)
                    .map(phases::add)
                    .orElseThrow(() -> new DistillationPhaseNotFoundException(id));
        }
        distillationPlan.setDistillationPhases(phases);
        LOGGER.info("Phase updated. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
        return DistillationPlanDto.toDistillationPlanDto(distillationPlan);
    }

    public DistillationPlanDto getDistillationPlan(Long id) {
        return DistillationPlanDto.toDistillationPlanDto((distillationPlanRepository.findById(id)).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    public DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto) {
        DistillationPlan distillationPlan = new DistillationPlan();
        distillationPlan.setName(createDistillationPlanDto.getName());
        distillationPlan.setDescription(createDistillationPlanDto.getDescription());
        return DistillationPlanDto.toDistillationPlanDto(distillationPlanRepository.save(distillationPlan));
    }

    @Override
    public void deleteDistillationPlan(Long id) {
        distillationPlanRepository.delete(distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    private DistillationPlan findById(Long id) {
        return distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id));
    }
}
