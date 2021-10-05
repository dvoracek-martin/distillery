package com.dvoracek.distillery.service.distillation.plan;


import java.util.List;

public class UpdateDistillationPlanDto {
    private String name;
    private String description;
    private List<Long> phaseIds;

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

    public List<Long> getPhaseIds() {
        return phaseIds;
    }

    public UpdateDistillationPlanDto setPhaseIds(List<Long> phaseIds) {
        this.phaseIds = phaseIds;
        return this;
    }
}
