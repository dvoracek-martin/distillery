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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "plan_id")
    private DistillationPlan plan;

    private double temperature;
    private double flow;
    private Long time;

    public DistillationPlan getPlan() {
        return plan;
    }

    public void setPlan(DistillationPlan plan) {
        this.plan = plan;
    }

    public DistillationPhase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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

    public DistillationPhase(Long id, String name, DistillationPlan plan, double temperature, double flow, Long time) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.temperature = temperature;
        this.flow = flow;
        this.time = Duration.ofMinutes(time).toMillis();
    }
}
