package com.dvoracek.distillery.service.distillation.phase;


import com.dvoracek.distillery.domain.phase.DistillationPhase;

public class DistillationPhaseDto {
    private Long id;

    private String name;

    private Long planId;

    private String rule;

    public Long getId() {
        return id;
    }

    public DistillationPhaseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DistillationPhaseDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public DistillationPhaseDto setRule(String rule) {
        this.rule = rule;
        return this;
    }


    public Long getPlanId() {
        return planId;
    }

    public DistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public static DistillationPhaseDto toDistillationPhaseDto(DistillationPhase distillationPhase) {
        return new DistillationPhaseDto()
                .setId(distillationPhase.getId())
                .setName(distillationPhase.getName())
                .setRule(distillationPhase.getRule())
                .setPlanId(distillationPhase.getPlan().getId());
    }
}
