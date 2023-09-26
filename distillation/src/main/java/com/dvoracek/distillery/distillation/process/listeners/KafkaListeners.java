package com.dvoracek.distillery.distillation.process.listeners;

import com.dvoracek.distillery.distillation.process.service.DistillationProcessService;
import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataFromRaspiDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListeners.class);

    private final DistillationProcessService distillationProcessService;

    public KafkaListeners(DistillationProcessService distillationProcessService) {
        this.distillationProcessService = distillationProcessService;
    }

    @KafkaListener(topics = "distillation-started", groupId = "distillery-backend")
    void distillationStartedListener(String data) {
        distillationProcessService.startDistillation(Long.parseLong(data));
    }

    @KafkaListener(topics = "distillation-plan-edited", groupId = "distillery-backend")
    void distillationEditedListener(String data) {
        distillationProcessService.shallReloadDistillationPlan(true);
    }

    @KafkaListener(topics = "distillation-progress-raspberry", groupId = "distillery-backend")
    void distillationProcessRaspberryListener(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        DistillationProcessDataFromRaspiDto distillationProcessDataFromRaspiDto = null;
        try {
            distillationProcessDataFromRaspiDto = objectMapper.readValue(data, DistillationProcessDataFromRaspiDto.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        }
        distillationProcessService.updateDistillationProcessData(distillationProcessDataFromRaspiDto);
    }


    @KafkaListener(topics = "distillation-progress-backend", groupId = "distillery-backend")
    void distillationProgressBackendListener(String data) {
        distillationProcessService.logBackendProgress(data);
    }

    @KafkaListener(topics = "distillation-terminated", groupId = "distillery-backend")
    void distillationTerminatedListener(String data) {
        distillationProcessService.terminateProcess(Long.parseLong(data));
    }

    @KafkaListener(topics = "distillation-terminated-by-user", groupId = "distillery-backend")
    void distillationTerminatedByUserListener(String data) {
        distillationProcessService.terminateProcessByUser(Long.parseLong(data));
    }

    @KafkaListener(topics = "distillation-next-phase", groupId = "distillery-backend")
    void distillationNextPhaseListener(String data) {
        distillationProcessService.nextPhase();
    }
}
