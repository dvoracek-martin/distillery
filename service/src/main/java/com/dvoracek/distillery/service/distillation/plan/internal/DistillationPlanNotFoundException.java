package com.dvoracek.distillery.service.distillation.plan.internal;

public class DistillationPlanNotFoundException extends RuntimeException {
    public DistillationPlanNotFoundException(Long id) {
        super("Phase with an id: " + id + " wasn't found.");
    }
}
