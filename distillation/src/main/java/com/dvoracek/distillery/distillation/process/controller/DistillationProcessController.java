package com.dvoracek.distillery.distillation.process.controller;

import com.dvoracek.distillery.distillation.process.service.DistillationProcessService;
import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataToFrontendDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DistillationProcessController {

    private final DistillationProcessService distillationProcessService;

    public DistillationProcessController(DistillationProcessService distillationProcessService) {
        this.distillationProcessService = distillationProcessService;
    }
    @MessageMapping("/distillery-backend")
    @SendTo("/topic/distillery-frontend")
    public DistillationProcessDataToFrontendDto getDistillationProcessData() {
        return distillationProcessService.getDataForFrontend();
    }
}
