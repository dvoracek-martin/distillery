package com.dvoracek.distillery.domain.measurement;

import javax.persistence.*;

@Entity
@Table(name = "T_MEASUREMENT")
public class DistillationMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double temperature;
    private double flow;
    private double weight;
    private double alcLevel;
    private boolean pause;
    private boolean terminate;
    private boolean turnOn;
    private boolean waiting;

    public Long getId() {
        return id;
    }

    public DistillationMeasurement setId(Long id) {
        this.id = id;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationMeasurement setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationMeasurement setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public DistillationMeasurement setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public boolean isPause() {
        return pause;
    }

    public DistillationMeasurement setPause(boolean pause) {
        this.pause = pause;
        return this;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public DistillationMeasurement setTerminate(boolean terminate) {
        this.terminate = terminate;
        return this;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public DistillationMeasurement setTurnOn(boolean work) {
        this.turnOn = work;
        return this;
    }


    public double getAlcLevel() {
        return alcLevel;
    }

    public DistillationMeasurement setAlcLevel(double alcLevel) {
        this.alcLevel = alcLevel;
        return this;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public DistillationMeasurement setWaiting(boolean waiting) {
        this.waiting = waiting;
        return this;
    }
}
