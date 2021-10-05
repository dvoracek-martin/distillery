package com.dvoracek.distillery.domain.plan;

import com.dvoracek.distillery.domain.phase.DistillationPhase;

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
//    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
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

    public DistillationPlan setDescription(String description) {
        this.description = description;
        return this;
    }

//    public List<PurchasedProduct> getDistillationPhases() {
//        return distillationPhases;
//    }
//
//    public DistillationPlan setDistillationPhases(List<PurchasedProduct> distillationPhases) {
//        this.distillationPhases = distillationPhases;
//        return this;
//    }
//
//
//    private void handleProducts(List<DistillationPhase> products) {
//        this.distillationPhases = products.stream()
//                .map(PurchasedProduct::new)
//                .collect(Collectors.toList());
//    }
}
