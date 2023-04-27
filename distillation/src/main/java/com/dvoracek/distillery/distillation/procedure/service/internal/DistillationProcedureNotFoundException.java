package com.dvoracek.distillery.distillation.procedure.service.internal;

public class DistillationProcedureNotFoundException extends RuntimeException {
    public DistillationProcedureNotFoundException(Long id) {
        super(String.format("Procedure with an id: %d wasn't found.", id));
    }
}
