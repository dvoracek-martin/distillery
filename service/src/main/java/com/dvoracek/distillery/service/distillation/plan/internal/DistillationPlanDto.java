package com.dvoracek.distillery.service.distillation.plan.internal;


import com.dvoracek.distillery.domain.plan.DistillationPlan;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseDto;

import java.util.List;
import java.util.stream.Collectors;

public class DistillationPlanDto {
    private Long id;

    private String name;

    private String description;

    private List<DistillationPhaseDto> distillationPhases;

    public Long getId() {
        return id;
    }

    public DistillationPlanDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DistillationPlanDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DistillationPlanDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<DistillationPhaseDto> getDistillationPhases() {
        return distillationPhases;
    }

    public DistillationPlanDto setDistillationPhases(List<DistillationPhaseDto> distillationPhases) {
        this.distillationPhases = distillationPhases;
        return this;
    }

    public static DistillationPlanDto toDistillationPlanDto(DistillationPlan distillationPlan) {
        return new DistillationPlanDto()
                .setId(distillationPlan.getId())
                .setName(distillationPlan.getName())
                .setDescription(distillationPlan.getDescription())
                .setDistillationPhases(distillationPlan.getDistillationPhases()
                        .stream().map(DistillationPhaseDto::toDistillationPhaseDto).collect(Collectors.toList()));

    }
}
