package com.dvoracek.distillery.distillation.phase.model;

import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Objects;

@Entity
@Table(name = "T_DISTILLATION_PHASE")
public class DistillationPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "plan_id")
    private DistillationPlan plan;

    private double temperature;
    private double flow;
    private Long time;

    public DistillationPhase(Long id, String name, double temperature, double flow, Long time) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.flow = flow;
        this.time = time;
    }

    public DistillationPhase() {
    }

    public DistillationPhase(Long id, String name, DistillationPlan plan, double temperature, double flow, Long time) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.temperature = temperature;
        this.flow = flow;
        this.time = Duration.ofMinutes(time).toMillis();
    }

    public DistillationPhase(String name, double temperature, double flow, Long time) {
        this.name = name;
        this.temperature = temperature;
        this.flow = flow;
        this.time = Duration.ofMinutes(time).toMillis();
    }

    public Long getId() {
        return id;
    }

    public DistillationPhase setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DistillationPhase setName(String name) {
        this.name = name;
        return this;
    }

    public DistillationPlan getPlan() {
        return plan;
    }

    public DistillationPhase setPlan(DistillationPlan plan) {
        this.plan = plan;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public DistillationPhase setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getFlow() {
        return flow;
    }

    public DistillationPhase setFlow(double flow) {
        this.flow = flow;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public DistillationPhase setTime(Long time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistillationPhase that)) return false;
        return Double.compare(that.temperature, temperature) == 0 && Double.compare(that.flow, flow) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(plan, that.plan) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, plan, temperature, flow, time);
    }
}
