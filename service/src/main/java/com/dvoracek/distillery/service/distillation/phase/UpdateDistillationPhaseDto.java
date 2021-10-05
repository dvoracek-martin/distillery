package com.dvoracek.distillery.service.distillation.phase;


public class UpdateDistillationPhaseDto {

    private String name;

    private Long planId;

    private String rule;

    public String getName() {
        return name;
    }

    public UpdateDistillationPhaseDto setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public UpdateDistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public UpdateDistillationPhaseDto setRule(String rule) {
        this.rule = rule;
        return this;
    }
}
