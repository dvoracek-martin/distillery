package com.dvoracek.distillery.service.distillation.measurement.internal;

public class CreateDistillationMeasurementDto {
    private Long temperature;
    private Long flow;
    private Long weight;
    private Long time;

    public Long getTemperature() {
        return temperature;
    }

    public CreateDistillationMeasurementDto setTemperature(Long temperature) {
        this.temperature = temperature;
        return this;
    }

    public Long getFlow() {
        return flow;
    }

    public CreateDistillationMeasurementDto setFlow(Long flow) {
        this.flow = flow;
        return this;
    }

    public Long getWeight() {
        return weight;
    }

    public CreateDistillationMeasurementDto setWeight(Long weight) {
        this.weight = weight;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public CreateDistillationMeasurementDto setTime(Long time) {
        this.time = time;
        return this;
    }
}
