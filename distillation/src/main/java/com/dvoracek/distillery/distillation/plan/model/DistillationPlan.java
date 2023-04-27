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
    @Column(name = "id", unique = true)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "plan", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private List<DistillationPhase> distillationPhases = new ArrayList<>();

    public DistillationPlan(Long id, String name, String description, List<DistillationPhase> distillationPhases) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.distillationPhases = distillationPhases;
    }

    public DistillationPlan() {
    }

    public List<DistillationPhase> getDistillationPhases() {
        return distillationPhases;
    }

    public void setDistillationPhases(List<DistillationPhase> distillationPhases) {
        this.distillationPhases = distillationPhases;
    }

    public Long getId() {
        return id;
    }

    public DistillationPlan setId(Long id) {
        this.id = id;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistillationPlan that)) return false;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return distillationPhases != null ? distillationPhases.equals(that.distillationPhases) : that.distillationPhases == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (distillationPhases != null ? distillationPhases.hashCode() : 0);
        return result;
    }
}
