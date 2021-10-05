package com.dvoracek.distillery.service.distillation.phase;


public class CreateDistillationPhaseDto {
    private Long id;

    private String name;

    private Long planId;

    private String rule;

    public Long getPlanId() {
        return planId;
    }

    public CreateDistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CreateDistillationPhaseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateDistillationPhaseDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public CreateDistillationPhaseDto setRule(String rule) {
        this.rule = rule;
        return this;
    }
}
