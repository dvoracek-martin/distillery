package com.dvoracek.distillery.service.distillation.exchange.data.internal;

import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeData;

public class DistillationExchangeDataDto {
    private Long id;
    private Long planId;
    private Long currentPhaseId;
    private Long timeElapsed;
    private Long timestamp;
    private double temperature;
    private double flow;
    private double weight;
    private double alcLevel;
    private boolean waiting;
    private boolean terminate;
    private boolean turnOn;

    public static DistillationExchangeDataDto toDto(DistillationExchangeData distillationExchangeData) {
        if (distillationExchangeData==null){
            return null;
        }
        return new DistillationExchangeDataDto()
                .setTemperature(distillationExchangeData.getTemperature())
                .setFlow(distillationExchangeData.getFlow())
                .setId(distillationExchangeData.getId())
                .setPlanId(distillationExchangeData.getPlanId())
                .setCurrentPhaseId(distillationExchangeData.getCurrentPhaseId())
                .setWeight(distillationExchangeData.getWeight())
                .setWaiting(distillationExchangeData.isWaiting())
                .setTerminate(distillationExchangeData.isTerminate())
                .setTurnOn(distillationExchangeData.isTurnOn())
                .setTimeElapsed(distillationExchangeData.getTimeElapsed())
                .setTimestamp(distillationExchangeData.getTimestamp());
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public DistillationExchangeDataDto setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public DistillationExchangeDataDto setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public Long getCurrentPhaseId() {
        return currentPhaseId;
    }

    public DistillationExchangeDataDto setCurrentPhaseId(Long currentPhaseId) {
        this.currentPhaseId = currentPhaseId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public DistillationExchangeDataDto setId(Long id) {
        this.id = id;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationExchangeDataDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationExchangeDataDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public DistillationExchangeDataDto setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public DistillationExchangeDataDto setWaiting(boolean waiting) {
        this.waiting = waiting;
        return this;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public DistillationExchangeDataDto setTerminate(boolean terminate) {
        this.terminate = terminate;
        return this;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public DistillationExchangeDataDto setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
        return this;
    }

    public double getAlcLevel() {
        return alcLevel;
    }

    public DistillationExchangeDataDto setAlcLevel(double alcLevel) {
        this.alcLevel = alcLevel;
        return this;
    }

    public Long getTimeElapsed() {
        return timeElapsed;
    }

    public DistillationExchangeDataDto setTimeElapsed(Long timeElapsed) {
        this.timeElapsed = timeElapsed;
        return this;
    }
}