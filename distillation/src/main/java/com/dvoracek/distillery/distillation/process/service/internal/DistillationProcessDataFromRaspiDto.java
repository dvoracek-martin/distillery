package com.dvoracek.distillery.distillation.process.service.internal;

public class DistillationProcessDataFromRaspiDto {
    private long timeStartedInMillis;
    private long distillationProcedureId;
    private double temperature;
    private double flow;
    private double weight;

    public long getTimeStartedInMillis() {
        return timeStartedInMillis;
    }

    public DistillationProcessDataFromRaspiDto setTimeStartedInMillis(long timeStartedInMillis) {
        this.timeStartedInMillis = timeStartedInMillis;
        return this;
    }

    public long getDistillationProcedureId() {
        return distillationProcedureId;
    }

    public DistillationProcessDataFromRaspiDto setDistillationProcedureId(long distillationProcedureId) {
        this.distillationProcedureId = distillationProcedureId;
        return this;
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
}
