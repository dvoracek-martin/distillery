package com.dvoracek.distillery.service.distillation.plan.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DistillationPlanEventListener implements ApplicationListener<DistillationPlanEvent> {


    @Override
    public void onApplicationEvent(DistillationPlanEvent event) {
        if (event instanceof DistillationPlanStartEvent){
            System.out.println("Start of a distillation plan");
        } else if (event instanceof DistillationPlanEndEvent){
            System.out.println("End of a distillation plan");
        }
    }

}