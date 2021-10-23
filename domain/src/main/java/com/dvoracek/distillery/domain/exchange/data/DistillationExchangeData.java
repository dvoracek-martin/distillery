package com.dvoracek.distillery.domain.exchange.data;

import javax.persistence.*;

@Entity
@Table(name = "T_EXCHANGE_DATA")
public class DistillationExchangeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public DistillationExchangeData setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public DistillationExchangeData setPlanId(Long planId) {
        this.planId = planId;
        return this;
    }

    public Long getCurrentPhaseId() {
        return currentPhaseId;
    }

    public DistillationExchangeData setCurrentPhaseId(Long currentPhaseId) {
        this.currentPhaseId = currentPhaseId;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationExchangeData setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationExchangeData setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public DistillationExchangeData setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public DistillationExchangeData setTerminate(boolean terminate) {
        this.terminate = terminate;
        return this;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public DistillationExchangeData setTurnOn(boolean work) {
        this.turnOn = work;
        return this;
    }


    public double getAlcLevel() {
        return alcLevel;
    }

    public DistillationExchangeData setAlcLevel(double alcLevel) {
        this.alcLevel = alcLevel;
        return this;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public DistillationExchangeData setWaiting(boolean waiting) {
        this.waiting = waiting;
        return this;
    }

    public Long getTimeElapsed() {
        return timeElapsed;
    }

    public DistillationExchangeData setTimeElapsed(Long timeElapsed) {
        this.timeElapsed = timeElapsed;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public DistillationExchangeData setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
