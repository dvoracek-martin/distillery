package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;

public class DistillationPlanStartEvent extends  DistillationPlanEvent {
    public DistillationPlanStartEvent(Object source, DistillationPlanDto distillationPlanDto) {
        super(source, distillationPlanDto);
    }
}
