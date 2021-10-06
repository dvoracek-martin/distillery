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

    private String temperature;
    private String flow;
    private String volume;
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


    public String getTemperature() {
        return temperature;
    }

    public DistillationPhase setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public DistillationPhase setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public DistillationPhase setVolume(String volume) {
        this.volume = volume;
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
