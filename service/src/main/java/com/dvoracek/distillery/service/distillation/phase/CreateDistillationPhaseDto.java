package com.dvoracek.distillery.service.distillation.phase;


public class CreateDistillationPhaseDto {

    private String name;
    private Long planId;
    private String temperature;
    private String flow;
    private String volume;
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

    public String getTemperature() {
        return temperature;
    }

    public CreateDistillationPhaseDto setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public CreateDistillationPhaseDto setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public CreateDistillationPhaseDto setVolume(String volume) {
        this.volume = volume;
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
