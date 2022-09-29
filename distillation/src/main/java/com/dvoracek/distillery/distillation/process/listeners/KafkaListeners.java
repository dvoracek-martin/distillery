package com.dvoracek.distillery.distillation.process.listeners;

import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataFromRaspiDto;
import com.dvoracek.distillery.distillation.process.service.DistillationProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final DistillationProcessService distillationProcessService;

    public KafkaListeners(DistillationProcessService distillationProcessService) {
        this.distillationProcessService = distillationProcessService;
    }

    @KafkaListener(topics = "distillation-started", groupId = "distillery-backend")
    void distillationStartedListener(String data) {
        distillationProcessService.startDistillation(Long.parseLong(data));
        System.out.println("Received " + data);
    }

    @KafkaListener(topics = "distillation-plan-edited", groupId = "distillery-backend")
    void distillationEditedListener(String data) {
        distillationProcessService.shallReloadDistillationPlan(true);
        System.out.println("Received " + data);
    }

    @KafkaListener(topics = "distillation-progress-raspberry", groupId = "distillery-backend")
    void distillationProcessRaspberryListener(String data) {
//        System.out.println("Received from raspi" + data);
        ObjectMapper objectMapper = new ObjectMapper();
        DistillationProcessDataFromRaspiDto distillationProcessDataFromRaspiDto;
        try {
            distillationProcessDataFromRaspiDto = objectMapper.readValue(data, DistillationProcessDataFromRaspiDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        distillationProcessService.updateDistillationProcessData(distillationProcessDataFromRaspiDto);
    }

    @KafkaListener(topics = "distillation-terminated", groupId = "distillery-backend")
    void distillationTerminatedListener(String data) {
        distillationProcessService.terminateProcess(Long.parseLong(data));
    }

    @KafkaListener(topics = "distillation-next-phase", groupId = "distillery-backend")
    void distillationNextPhaseListener(String data) {
        distillationProcessService.nextPhase();
    }
}
