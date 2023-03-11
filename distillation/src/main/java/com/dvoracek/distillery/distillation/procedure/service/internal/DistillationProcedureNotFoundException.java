package com.dvoracek.distillery.distillation.procedure.service.internal;

public class DistillationProcedureNotFoundException extends RuntimeException {
    public DistillationProcedureNotFoundException(Long id) {
        super("Procedure with an id: " + id + " wasn't found.");
    }
}
