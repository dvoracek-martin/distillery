package com.dvoracek.distillery.service.distillation.phase;


import com.dvoracek.distillery.domain.phase.DistillationPhase;

public class DistillationPhaseDto {

    private Long id;
    private String name;
    private Long planId;
    private String temperature;
    private String flow;
    private String volume;
    private Long time;

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

    public String getTemperature() {
        return temperature;
    }

    public DistillationPhaseDto setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public DistillationPhaseDto setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public DistillationPhaseDto setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public DistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public DistillationPhaseDto setTime(Long time) {
        this.time = time;
        return this;
    }

    public static DistillationPhaseDto toDistillationPhaseDto(DistillationPhase distillationPhase) {
        return new DistillationPhaseDto()
                .setId(distillationPhase.getId())
                .setName(distillationPhase.getName())
                .setTemperature(distillationPhase.getTemperature())
                .setFlow(distillationPhase.getFlow())
                .setVolume(distillationPhase.getVolume())
                .setPlanId(distillationPhase.getPlan().getId())
                .setTime(distillationPhase.getTime());
    }
}
