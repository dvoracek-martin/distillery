package com.dvoracek.distillery.service.distillation.measurement.internal;

import com.dvoracek.distillery.domain.measurement.DistillationMeasurement;

public class DistillationMeasurementDto {
    private Long id;
    private double temperature;
    private double flow;
    private double weight;
    private double alcLevel;
    private boolean pause;
    private boolean terminate;
    private boolean turnOn;

    public static DistillationMeasurementDto toDto(DistillationMeasurement distillationMeasurement) {
        return new DistillationMeasurementDto()
                .setTemperature(distillationMeasurement.getTemperature())
                .setFlow(distillationMeasurement.getFlow())
                .setId(distillationMeasurement.getId())
                .setWeight(distillationMeasurement.getWeight())
                .setPause(distillationMeasurement.isPause())
                .setTerminate(distillationMeasurement.isTerminate())
                .setTurnOn(distillationMeasurement.isTurnOn());
    }

    public Long getId() {
        return id;
    }

    public DistillationMeasurementDto setId(Long id) {
        this.id = id;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationMeasurementDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationMeasurementDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public DistillationMeasurementDto setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public boolean isPause() {
        return pause;
    }

    public DistillationMeasurementDto setPause(boolean pause) {
        this.pause = pause;
        return this;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public DistillationMeasurementDto setTerminate(boolean terminate) {
        this.terminate = terminate;
        return this;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public DistillationMeasurementDto setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
        return this;
    }

    public double getAlcLevel() {
        return alcLevel;
    }

    public DistillationMeasurementDto setAlcLevel(double alcLevel) {
        this.alcLevel = alcLevel;
        return this;
    }
}