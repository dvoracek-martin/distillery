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

    private String rule;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public DistillationPhase setRule(String rule) {
        this.rule = rule;
        return this;
    }
}
