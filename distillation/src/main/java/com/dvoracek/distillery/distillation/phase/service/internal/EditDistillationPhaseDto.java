package com.dvoracek.distillery.distillation.phase.service.internal;


public class EditDistillationPhaseDto {

    private String name;
    private Long planId;
    private Long id;
    private double temperature;
    private double flow;
    private Long time;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public EditDistillationPhaseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public EditDistillationPhaseDto setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public EditDistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public EditDistillationPhaseDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public EditDistillationPhaseDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public EditDistillationPhaseDto setTime(Long time) {
        this.time = time;
        return this;
    }
}
