package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import org.springframework.context.ApplicationEvent;

public class DistillationPlanEvent extends ApplicationEvent {
    private DistillationPlanDto distillationPlanDto;

    public DistillationPlanEvent(Object source, DistillationPlanDto distillationPlanDto) {
        super(source);
        this.distillationPlanDto = distillationPlanDto;
    }

    public DistillationPlanDto getDistillationPlanDto() {
        return distillationPlanDto;
    }
}