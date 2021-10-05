package com.dvoracek.distillery.service.distillation.plan;

public class DistillationPlanNotFoundException extends RuntimeException {
    public DistillationPlanNotFoundException(Long id) {
        super("Phase with an id: " + id + " wasn't found.");
    }
}
