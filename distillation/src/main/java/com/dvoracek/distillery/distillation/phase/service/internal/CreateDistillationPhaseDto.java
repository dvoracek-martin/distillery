package com.dvoracek.distillery.distillation.phase.service.internal;


public class CreateDistillationPhaseDto {
    private String name;
    private Long planId;
    private double temperature;
    private double flow;
    private Long time;

    public Long getPlanId() {
        return planId;
    }

    public CreateDistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateDistillationPhaseDto setName(String name) {
        this.name = name;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public CreateDistillationPhaseDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public CreateDistillationPhaseDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public CreateDistillationPhaseDto setTime(Long time) {
        this.time = time;
        return this;
    }

}
