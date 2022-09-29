package com.dvoracek.distillery.distillation.process.service.internal;

import com.dvoracek.distillery.distillation.phase.service.internal.DistillationPhaseDto;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;

public class DistillationProcessDataToFrontendDto {

    private DistillationPhaseDto currentDistillationPhaseDto;
    private DistillationPlanDto distillationPlanDto;
    private double temperature;
    private double flow;
    private double weight;
    private boolean isTerminated;
    private boolean isEnergyOn;
    private boolean isPaused;
    private Long timeElapsedInMillis;

    private Double alcLevel;

    public DistillationPhaseDto getCurrentDistillationPhaseDto() {
        return currentDistillationPhaseDto;
    }

    public void setCurrentDistillationPhaseDto(DistillationPhaseDto currentDistillationPhaseDto) {
        this.currentDistillationPhaseDto = currentDistillationPhaseDto;
    }

    public DistillationPlanDto getDistillationPlanDto() {
        return distillationPlanDto;
    }

    public void setDistillationPlanDto(DistillationPlanDto distillationPlanDto) {
        this.distillationPlanDto = distillationPlanDto;
    }

    public boolean isEnergyOn() {
        return isEnergyOn;
    }

    public void setEnergyOn(boolean energyOn) {
        isEnergyOn = energyOn;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean isTerminated) {
        this.isTerminated = isTerminated;
    }

    public Long getTimeElapsedInMillis() {
        return timeElapsedInMillis;
    }

    public void setTimeElapsedInMillis(Long timeElapsedInMillis) {
        this.timeElapsedInMillis = timeElapsedInMillis;
    }

    public Double getAlcLevel() {
        return alcLevel;
    }

    public void setAlcLevel(Double alcLevel) {
        this.alcLevel = alcLevel;
    }

    public DistillationProcessDataToFrontendDto() {
    }

    public DistillationProcessDataToFrontendDto(DistillationPlanDto distillationPlanDto, DistillationPhaseDto currentDistillationPhaseDto, double temperature, double flow, double weight, Long timeElapsedInMillis, boolean isTerminated, boolean isPaused, boolean isEnergyOn, Double alcLevel) {
        this.distillationPlanDto = distillationPlanDto;
        this.currentDistillationPhaseDto = currentDistillationPhaseDto;
        this.temperature = temperature;
        this.flow = flow;
        this.weight = weight;
        this.isTerminated = isTerminated;
        this.isPaused = isPaused;
        this.isEnergyOn = isEnergyOn;
        this.timeElapsedInMillis = timeElapsedInMillis;
        this.alcLevel = alcLevel;
    }
}
