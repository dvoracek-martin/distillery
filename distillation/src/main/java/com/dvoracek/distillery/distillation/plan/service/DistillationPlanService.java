package com.dvoracek.distillery.distillation.plan.service;

import com.dvoracek.distillery.distillation.plan.service.internal.CreateDistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.EditDistillationPlanDto;

import java.util.List;

public interface DistillationPlanService {
    List<DistillationPlanDto> getAll();

    DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto);

    void deleteDistillationPlan(Long id);

    DistillationPlanDto editPlan(Long id, EditDistillationPlanDto editDistillationPlanDto);

    DistillationPlanDto getDistillationPlan(Long id);

    void startDistillation(DistillationPlanDto distillationPlanDto);

    void terminateDistillation(DistillationPlanDto distillationPlanDto);

    void jumpToNextPhase(DistillationPlanDto distillationPlanDto);
}
