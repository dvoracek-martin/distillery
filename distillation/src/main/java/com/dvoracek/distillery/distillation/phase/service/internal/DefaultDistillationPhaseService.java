package com.dvoracek.distillery.distillation.phase.service.internal;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.repository.DistillationPhaseRepository;
import com.dvoracek.distillery.distillation.phase.service.DistillationPhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DefaultDistillationPhaseService implements DistillationPhaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPhaseService.class);

    private final DistillationPhaseRepository distillationPhaseRepository;

    public DefaultDistillationPhaseService(DistillationPhaseRepository distillationPhaseRepository) {
        this.distillationPhaseRepository = distillationPhaseRepository;
    }

    @Override
    public DistillationPhase createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto) {
        DistillationPhase distillationPhase = new DistillationPhase(
                createDistillationPhaseDto.getName(),
                createDistillationPhaseDto.getTemperature(),
                createDistillationPhaseDto.getFlow(),
                createDistillationPhaseDto.getTime()
        );
        LOGGER.info("Phase created. ID: {}, name: {}", distillationPhase.getId(), distillationPhase.getName());
        return distillationPhase;
    }

    @Override
    public DistillationPhase findById(Long id) {
        return distillationPhaseRepository.findById(id).orElseThrow(() -> new DistillationPhaseNotFoundException(id));
    }
}
