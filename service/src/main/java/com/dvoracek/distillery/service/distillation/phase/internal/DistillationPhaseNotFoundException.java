package com.dvoracek.distillery.service.distillation.phase.internal;

public class DistillationPhaseNotFoundException extends RuntimeException {
    public DistillationPhaseNotFoundException(Long id) {
        super("Phase with an id: " + id + " wasn't found.");
    }
}
