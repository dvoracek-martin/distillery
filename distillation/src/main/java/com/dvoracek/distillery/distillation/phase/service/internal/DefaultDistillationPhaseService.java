package com.dvoracek.distillery.distillation.phase.service.internal;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.repository.DistillationPhaseRepository;
import com.dvoracek.distillery.distillation.phase.service.DistillationPhaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DefaultDistillationPhaseService implements DistillationPhaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPhaseService.class);

    private final DistillationPhaseRepository distillationPhaseRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public DefaultDistillationPhaseService(DistillationPhaseRepository distillationPhaseRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.distillationPhaseRepository = distillationPhaseRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public DistillationPhase createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto) {
        DistillationPhase distillationPhase = new DistillationPhase();
        distillationPhase.setName(createDistillationPhaseDto.getName());
        distillationPhase.setTemperature(createDistillationPhaseDto.getTemperature());
        distillationPhase.setFlow(createDistillationPhaseDto.getFlow());
        distillationPhase.setTime(createDistillationPhaseDto.getTime());
        distillationPhaseRepository.saveAndFlush(distillationPhase);
        kafkaTemplate.send("distillation-phase-created", Long.toString(distillationPhase.getId()));
        LOGGER.info("Phase created. ID: {}, name: {}", distillationPhase.getId(), distillationPhase.getName());
        return distillationPhase;
    }

    @Override
    public DistillationPhase findById(Long id) {
        return distillationPhaseRepository.findById(id).orElseThrow(() -> new DistillationPhaseNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        distillationPhaseRepository.delete(findById(id));
        distillationPhaseRepository.flush();
    }
}
