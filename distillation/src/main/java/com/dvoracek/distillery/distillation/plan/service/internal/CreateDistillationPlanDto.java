package com.dvoracek.distillery.distillation.plan.service.internal;

import com.dvoracek.distillery.distillation.phase.service.internal.CreateDistillationPhaseDto;

import java.util.List;

public class CreateDistillationPlanDto {
    private String name;
    private String description;
    private List<CreateDistillationPhaseDto> distillationPhases;

    public List<CreateDistillationPhaseDto> getDistillationPhases() {
        return distillationPhases;
    }

    public CreateDistillationPlanDto setDistillationPhases(List<CreateDistillationPhaseDto> distillationPhases) {
        this.distillationPhases = distillationPhases;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateDistillationPlanDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateDistillationPlanDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
