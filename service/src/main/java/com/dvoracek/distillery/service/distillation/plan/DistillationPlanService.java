package com.dvoracek.distillery.service.distillation.plan;

import com.dvoracek.distillery.service.distillation.plan.internal.CreateDistillationPlanDto;
import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import com.dvoracek.distillery.service.distillation.plan.internal.UpdateDistillationPlanDto;

import java.util.List;

public interface DistillationPlanService {
    List<DistillationPlanDto> getAll();

    DistillationPlanDto updatePlan(Long id, UpdateDistillationPlanDto updateDistillationPlanDto);

    DistillationPlanDto getDistillationPlan(Long id);

    DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto);

    void deleteDistillationPlan(Long id);

    void startDistillation(DistillationPlanDto distillationPlanDto);

    void terminateDistillation(DistillationPlanDto distillationPlanDto);
}
