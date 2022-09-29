package com.dvoracek.distillery.distillation.plan.model;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_DISTILLATION_PLAN")
public class DistillationPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column()
    private String name;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "plan", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private List<DistillationPhase> distillationPhases = new ArrayList<>();

    public List<DistillationPhase> getDistillationPhases() {
        return distillationPhases;
    }

    public DistillationPlan setDistillationPhases(List<DistillationPhase> distillationPhases) {
        this.distillationPhases = distillationPhases;
        return this;
    }

    public DistillationPlan() {
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

    public DistillationPlan setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
