package com.dvoracek.distillery.service.distillation.plan;

import java.util.List;

public interface DistillationPlanService {
    List<DistillationPlanDto> getAll();

    DistillationPlanDto updatePlan(Long id, UpdateDistillationPlanDto updateDistillationPlanDto);

    DistillationPlanDto getDistillationPlan(Long id);

    DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto);

    void deleteDistillationPlan(Long id);
}
