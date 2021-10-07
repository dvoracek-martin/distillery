package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;

public class DistillationPlanEndEvent extends DistillationPlanEvent {
    public DistillationPlanEndEvent(Object source, DistillationPlanDto distillationPlanDto) {
        super(source, distillationPlanDto);
    }
}