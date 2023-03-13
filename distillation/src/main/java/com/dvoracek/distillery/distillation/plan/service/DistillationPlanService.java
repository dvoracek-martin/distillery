package com.dvoracek.distillery.distillation.plan.service;

import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import com.dvoracek.distillery.distillation.plan.service.internal.CreateDistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.EditDistillationPlanDto;

import java.util.List;

public interface DistillationPlanService {
    List<DistillationPlan> getAll();

    DistillationPlan createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto);

    void deleteDistillationPlan(Long id);

    DistillationPlan editPlan(Long id, EditDistillationPlanDto editDistillationPlanDto);

    DistillationPlan getDistillationPlan(Long id);

    void startDistillation(DistillationPlan distillationPlan);

    void terminateDistillation(DistillationPlan distillationPlan);

    void jumpToNextPhase(DistillationPlan distillationPlan);
}
