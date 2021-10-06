package com.dvoracek.distillery.service.distillation.phase;


public class UpdateDistillationPhaseDto {

    private String name;
    private Long planId;
    private Long id;
    private String temperature;
    private String flow;
    private String volume;
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

    public String getTemperature() {
        return temperature;
    }

    public UpdateDistillationPhaseDto setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public UpdateDistillationPhaseDto setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public UpdateDistillationPhaseDto setVolume(String volume) {
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
