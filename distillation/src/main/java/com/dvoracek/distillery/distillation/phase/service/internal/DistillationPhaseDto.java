package com.dvoracek.distillery.distillation.phase.service.internal;


import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;

public class DistillationPhaseDto {

    private Long id;
    private String name;
    private Long planId;
    private double temperature;
    private double flow;
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

    public Long getPlanId() {
        return planId;
    }

    public DistillationPhaseDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationPhaseDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationPhaseDto setFlow(double flow) {
        this.flow = flow;
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
                .setTime(distillationPhase.getTime());
    }

    public static DistillationPhase fromDistillationPhaseDto(DistillationPhaseDto distillationPhaseDto) {
        return new DistillationPhase(
                distillationPhaseDto.getId(),
                distillationPhaseDto.getName(),
                distillationPhaseDto.getTemperature(),
                distillationPhaseDto.getFlow(),
                distillationPhaseDto.getTime());
    }

}
