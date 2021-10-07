package com.dvoracek.distillery.service.distillation.plan.internal;


import com.dvoracek.distillery.service.distillation.phase.internal.UpdateDistillationPhaseDto;

import java.util.List;

public class UpdateDistillationPlanDto {
        private Long id;
    private String name;
    private String description;
    private List<UpdateDistillationPhaseDto> distillationPhases;

    public Long getId() {
        return id;
    }

    public UpdateDistillationPlanDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateDistillationPlanDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateDistillationPlanDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<UpdateDistillationPhaseDto> getDistillationPhases() {
        return distillationPhases;
    }

    public UpdateDistillationPlanDto setDistillationPhases(List<UpdateDistillationPhaseDto> distillationPhases) {
        this.distillationPhases = distillationPhases;
        return this;
    }
}
