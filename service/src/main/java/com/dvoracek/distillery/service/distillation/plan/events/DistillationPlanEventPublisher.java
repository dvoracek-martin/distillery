package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DistillationPlanEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public static boolean distillationLock = false;

    public DistillationPlanEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishDistillationPlanStartEvent(final DistillationPlanDto distillationPlanDto) {
        if (!distillationLock) {
            distillationLock = true;
            DistillationPlanEvent distillationPlanEvent = new DistillationPlanStartEvent(this, distillationPlanDto);
            applicationEventPublisher.publishEvent(distillationPlanEvent);
        }
    }

    public void publishDistillationPlanEndEvent(final DistillationPlanDto distillationPlanDto) {
        distillationLock = false;
        DistillationPlanEvent distillationPlanEvent = new DistillationPlanEndEvent(this, distillationPlanDto);
        applicationEventPublisher.publishEvent(distillationPlanEvent);
    }

    public void publishDistillationPlanUpdatedEvent(final DistillationPlanDto distillationPlanDto) {
        DistillationPlanEvent distillationPlanEvent = new DistillationPlanUpdatedEvent(this, distillationPlanDto);
        applicationEventPublisher.publishEvent(distillationPlanEvent);
    }

    public void publishDistillationPlanJumpToNextPhase() {
        DistillationPlanEvent distillationPlanEvent = new DistillationPlanJumpToNextPhase(this);
        applicationEventPublisher.publishEvent(distillationPlanEvent);
    }
}
