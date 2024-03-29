package com.dvoracek.distillery.distillation.procedure.service;

import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;
import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataFromRaspiDto;

import java.util.List;

public interface DistillationProcedureService {
    DistillationProcedure getDistillationProcedure(Long procedureId);
    DistillationProcedure getByPlanIdAndAttemptNumber(Long planId, int attemptNumber);
    DistillationProcedure getLastByPlan(Long planId);
    List<DistillationProcedure> getAll();
    DistillationProcedure createDistillationProcedure(Long planId);
    void terminateDistillationProcedure(Long procedureId);

    void terminateDistillationProcedureByUser(Long procedureId);
    void deleteDistillationProcedure(Long procedureId);
    List<DistillationProcessDataFromRaspiDto> getDistillationProcedureFromES(Long procedureId);
}
