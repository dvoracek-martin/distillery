package com.dvoracek.distillery.distillation.process.service.internal;

public class DistillationProcessDataFromRaspiDto {
    private double temperature;
    private double flow;
    private double weight;

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
