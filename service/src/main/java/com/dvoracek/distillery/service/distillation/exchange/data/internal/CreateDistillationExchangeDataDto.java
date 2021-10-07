package com.dvoracek.distillery.service.distillation.exchange.data.internal;

public class CreateDistillationExchangeDataDto {
    private Long planId;
    private Long currentPhaseId;
    private double temperature;
    private double flow;
    private double weight;
    private Long time;
    private boolean waiting;
    private boolean terminate;
    private boolean turnOn;

    public double getTemperature() {
        return temperature;
    }

    public CreateDistillationExchangeDataDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public CreateDistillationExchangeDataDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public CreateDistillationExchangeDataDto setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public CreateDistillationExchangeDataDto setTime(Long time) {
        this.time = time;
        return this;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public CreateDistillationExchangeDataDto setWaiting(boolean waiting) {
        this.waiting = waiting;
        return this;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public CreateDistillationExchangeDataDto setTerminate(boolean terminate) {
        this.terminate = terminate;
        return this;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public CreateDistillationExchangeDataDto setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public CreateDistillationExchangeDataDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public Long getCurrentPhaseId() {
        return currentPhaseId;
    }

    public CreateDistillationExchangeDataDto setCurrentPhaseId(Long currentPhaseId) {
        this.currentPhaseId = currentPhaseId;
        return this;
    }
}
