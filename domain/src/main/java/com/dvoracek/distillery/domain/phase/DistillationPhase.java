package com.dvoracek.distillery.domain.phase;

import com.dvoracek.distillery.domain.plan.DistillationPlan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_DISTILLATION_PHASE")
public class DistillationPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DistillationPlan plan;

    private double temperature;
    private double flow;
    private double weight;
    private Long time;

    public DistillationPlan getPlan() {
        return plan;
    }

    public DistillationPhase setPlan(DistillationPlan plan) {
        this.plan = plan;
        return this;
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

    public DistillationPhase setName(String name) {
        this.name = name;
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

    public double getWeight() {
        return weight;
    }

    public DistillationPhase setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public DistillationPhase setTime(Long time) {
        this.time = time;
        return this;
    }
}
