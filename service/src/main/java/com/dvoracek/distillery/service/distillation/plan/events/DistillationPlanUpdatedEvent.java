package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;

public class DistillationPlanUpdatedEvent extends DistillationPlanEvent {
    public DistillationPlanUpdatedEvent(DistillationPlanEventPublisher distillationPlanEventPublisher, DistillationPlanDto distillationPlanDto) {
        super(distillationPlanEventPublisher, distillationPlanDto);
    }
}
