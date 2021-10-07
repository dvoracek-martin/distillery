package com.dvoracek.distillery.service.distillation.phase.internal;


public class UpdateDistillationPhaseDto {

    private String name;
    private Long planId;
    private Long id;
    private double temperature;
    private double flow;
    private double volume;
    private Long time;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public UpdateDistillationPhaseDto setId(Long id) {
        this.id = id;
        return this;
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

    public double getTemperature() {
        return temperature;
    }

    public UpdateDistillationPhaseDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public UpdateDistillationPhaseDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getVolume() {
        return volume;
    }

    public UpdateDistillationPhaseDto setVolume(double volume) {
        this.volume = volume;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public UpdateDistillationPhaseDto setTime(Long time) {
        this.time = time;
        return this;
    }
}
