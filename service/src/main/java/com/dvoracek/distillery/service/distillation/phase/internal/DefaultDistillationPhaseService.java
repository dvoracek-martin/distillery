package com.dvoracek.distillery.service.distillation.phase.internal;

import com.dvoracek.distillery.domain.phase.DistillationPhase;
import com.dvoracek.distillery.domain.phase.DistillationPhaseRepository;
import com.dvoracek.distillery.domain.plan.DistillationPlanRepository;
import com.dvoracek.distillery.service.distillation.phase.*;
import com.dvoracek.distillery.service.distillation.plan.DistillationPlanNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultDistillationPhaseService implements DistillationPhaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPhaseService.class);

    private final DistillationPhaseRepository distillationPhaseRepository;
    private final DistillationPlanRepository distillationPlanRepository;

    public DefaultDistillationPhaseService(DistillationPhaseRepository distillationPhaseRepository, DistillationPlanRepository distillationPlanRepository) {
        this.distillationPhaseRepository = distillationPhaseRepository;
        this.distillationPlanRepository = distillationPlanRepository;
    }
    @Override
    public List<DistillationPhaseDto> getAll() {
        return distillationPhaseRepository.findAll().stream()
                .map(DistillationPhaseDto::toDistillationPhaseDto).collect(Collectors.toList());
    }

    @Override
    public DistillationPhaseDto updatePhase(Long id, UpdateDistillationPhaseDto updateDistillationPhaseDto) {
        DistillationPhase distillationPhase = findById(id);
        distillationPhase.setName(updateDistillationPhaseDto.getName());
        LOGGER.info("Phase updated. ID: {}, name: {}", distillationPhase.getId(), distillationPhase.getName());
        return DistillationPhaseDto.toDistillationPhaseDto(distillationPhase);
    }
    @Override
    public DistillationPhaseDto getDistillationPhase(Long id) {
        return DistillationPhaseDto.toDistillationPhaseDto((distillationPhaseRepository.findById(id)).orElseThrow(() -> new DistillationPhaseNotFoundException(id)));
    }
    @Override
    public DistillationPhaseDto createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto) {
        DistillationPhase distillationPhase = new DistillationPhase();
        distillationPhase.setName(createDistillationPhaseDto.getName());
        distillationPhase.setPlan(distillationPlanRepository.findById(createDistillationPhaseDto.getPlanId()).orElseThrow(()
                -> new DistillationPlanNotFoundException(createDistillationPhaseDto.getPlanId())));
        distillationPhase.setTemperature(createDistillationPhaseDto.getTemperature());
        distillationPhase.setFlow(createDistillationPhaseDto.getFlow());
        distillationPhase.setVolume(createDistillationPhaseDto.getVolume());
        distillationPhase.setTime(createDistillationPhaseDto.getTime());
        return DistillationPhaseDto.toDistillationPhaseDto(distillationPhaseRepository.save(distillationPhase));
    }

    @Override
    public void deleteDistillationPhase(Long id) {
        distillationPlanRepository.delete(distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    private DistillationPhase findById(Long id) {
        return distillationPhaseRepository.findById(id).orElseThrow(() -> new DistillationPhaseNotFoundException(id));
    }
}
