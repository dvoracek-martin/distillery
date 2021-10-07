package com.dvoracek.distillery.service.distillation.plan.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DistillationPlanEventListener implements ApplicationListener<DistillationPlanEvent> {


    @Override
    public void onApplicationEvent(DistillationPlanEvent event) {
        if (event instanceof DistillationPlanStartEvent){
            System.out.println("Start");
        } else if (event instanceof DistillationPlanEndEvent){
            System.out.println("End");
        }
        System.out.println("Received spring custom event - " + event.getDistillationPlanDto().getId());
    }

}