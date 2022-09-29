package com.dvoracek.distillery.distillation.phase.controller;

import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/phase")
@CrossOrigin(origins = "http://localhost:4200")
public class DistillationPhaseController {

    private final KafkaTemplate kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DistillationPhaseController.class);

    public DistillationPhaseController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/next")
    @ResponseStatus(HttpStatus.OK)
    public void jumpToNextPhase(DistillationPlan distillationPlanId) {
        LOGGER.debug("Received Http.POST /api/phase/post");
        kafkaTemplate.send("distillation-next-phase", Long.toString(distillationPlanId.getId()));
    }
}
