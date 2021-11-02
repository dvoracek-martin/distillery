package com.dvoracek.distillery.service.distillation.plan.events;

public class DistillationPlanJumpToNextPhase extends DistillationPlanEvent {
    public DistillationPlanJumpToNextPhase(DistillationPlanEventPublisher distillationPlanEventPublisher) {
        super(distillationPlanEventPublisher);
    }
}
