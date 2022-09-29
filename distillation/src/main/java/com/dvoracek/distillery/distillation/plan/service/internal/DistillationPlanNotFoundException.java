package com.dvoracek.distillery.distillation.plan.service.internal;

public class DistillationPlanNotFoundException extends RuntimeException {
    public DistillationPlanNotFoundException(Long id) {
        super("Phase with an id: " + id + " wasn't found.");
    }
}
