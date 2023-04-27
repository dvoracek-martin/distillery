package com.dvoracek.distillery.distillation.process.service.internal;

public class DistillationProcessDataToRaspiDto {

    private long timeStartedInMillis;
    private long timeElapsedSinceStartInMillis;
    private long distillationProcedureId;
    private long distillationPhaseId;
    private double temperature;
    private double flow;
    private double weight;

    public long getDistillationPhaseId() {
        return distillationPhaseId;
    }

    public void setDistillationPhaseId(long distillationPhaseId) {
        this.distillationPhaseId = distillationPhaseId;
    }

    public long getTimeElapsedSinceStartInMillis() {
        return timeElapsedSinceStartInMillis;
    }

    public void setTimeElapsedSinceStartInMillis(long timeElapsedSinceStartInMillis) {
        this.timeElapsedSinceStartInMillis = timeElapsedSinceStartInMillis;
    }

    public long getTimeStartedInMillis() {
        return timeStartedInMillis;
    }

    public void setTimeStartedInMillis(long timeStartedInMillis) {
        this.timeStartedInMillis = timeStartedInMillis;
    }

    public long getDistillationProcedureId() {
        return distillationProcedureId;
    }

    public void setDistillationProcedureId(long distillationProcedureId) {
        this.distillationProcedureId = distillationProcedureId;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationProcessDataToRaspiDto setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationProcessDataToRaspiDto setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public DistillationProcessDataToRaspiDto setWeight(double weight) {
        this.weight = weight;
        return this;
    }
}
