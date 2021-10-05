package com.dvoracek.distillery.service.distillation.plan;

import java.util.List;

public class CreateDistillationPlanDto {
    private String name;
    private String description;
    private List<Long> phaseIds;

    public List<Long> getPhaseIds() {
        return phaseIds;
    }

    public CreateDistillationPlanDto setPhaseIds(List<Long> phaseIds) {
        this.phaseIds = phaseIds;
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
