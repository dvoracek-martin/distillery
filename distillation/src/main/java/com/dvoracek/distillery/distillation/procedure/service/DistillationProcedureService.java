package com.dvoracek.distillery.distillation.procedure.service;

import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;

import java.util.List;

public interface DistillationProcedureService {

    DistillationProcedure getDistillationProcedure(Long id);
    DistillationProcedure getByPlanIdAndAttemptNumber(Long planId, int attemptNumber);
    DistillationProcedure getLastByPlan(Long planId);
    List<DistillationProcedure> getAll();
    DistillationProcedure createDistillationProcedure(Long planId);
    DistillationProcedure terminateDistillationProcedure(Long procedureId);
    DistillationProcedure deleteDistillationProcedure(Long id);
}
