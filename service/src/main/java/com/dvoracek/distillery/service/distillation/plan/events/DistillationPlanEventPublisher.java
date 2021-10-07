package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DistillationPlanEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private static boolean distillationLock = false;

    public DistillationPlanEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishDistillationPlanStartEvent(final DistillationPlanDto distillationPlanDto) {
        if (!distillationLock) {
            distillationLock = true;
            System.out.println("Distillation plan started. ");
            DistillationPlanEvent distillationPlanEvent = new DistillationPlanStartEvent(this, distillationPlanDto);
            applicationEventPublisher.publishEvent(distillationPlanEvent);
        }
    }

    public void publishDistillationPlanEndEvent(final DistillationPlanDto distillationPlanDto) {
        distillationLock = false;
        System.out.println("Distillation plan finished. ");
        DistillationPlanEvent distillationPlanEvent = new DistillationPlanEndEvent(this, distillationPlanDto);
        applicationEventPublisher.publishEvent(distillationPlanEvent);
    }
}