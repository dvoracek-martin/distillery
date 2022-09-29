package com.dvoracek.distillery.distillation.plan.service.internal;


import com.dvoracek.distillery.distillation.phase.service.internal.EditDistillationPhaseDto;

import java.util.List;

public class EditDistillationPlanDto {
    private Long id;
    private String name;
    private String description;
    private List<EditDistillationPhaseDto> distillationPhases;

    public Long getId() {
        return id;
    }

    public EditDistillationPlanDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditDistillationPlanDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditDistillationPlanDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<EditDistillationPhaseDto> getDistillationPhases() {
        return distillationPhases;
    }

    public EditDistillationPlanDto setDistillationPhases(List<EditDistillationPhaseDto> distillationPhases) {
        this.distillationPhases = distillationPhases;
        return this;
    }
}
