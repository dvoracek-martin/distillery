package com.dvoracek.distillery.distillation.procedure.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_DISTILLATION_PROCEDURE")
public class DistillationProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column()
    private Long distillationPlanId;

    @NotBlank
    @Size(max = 255)
    @Column()
    private String distillationPlanName;

    @NotBlank
    @Column()
    private int attemptNumber;

    @NotBlank
    @Column()
    private LocalDateTime distillationTimeStart;

    @NotBlank
    @Column()
    private LocalDateTime distillationTimeEnd;

    @NotBlank
    @Column()
    private DistillationEndReason distillationEndReason;

    public Long getId() {
        return id;
    }

    public DistillationProcedure setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getDistillationPlanId() {
        return distillationPlanId;
    }

    public DistillationProcedure setDistillationPlanId(Long distillationPlanId) {
        this.distillationPlanId = distillationPlanId;
        return this;
    }

    public String getDistillationPlanName() {
        return distillationPlanName;
    }

    public DistillationProcedure setDistillationPlanName(String distillationPlanName) {
        this.distillationPlanName = distillationPlanName;
        return this;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public DistillationProcedure setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
        return this;
    }

    public LocalDateTime getDistillationTimeStart() {
        return distillationTimeStart;
    }

    public DistillationProcedure setDistillationTimeStart(LocalDateTime distillationTimeStart) {
        this.distillationTimeStart = distillationTimeStart;
        return this;
    }

    public LocalDateTime getDistillationTimeEnd() {
        return distillationTimeEnd;
    }

    public DistillationProcedure setDistillationTimeEnd(LocalDateTime distillationTimeEnd) {
        this.distillationTimeEnd = distillationTimeEnd;
        return this;
    }

    public DistillationEndReason getDistillationEndReason() {
        return distillationEndReason;
    }

    public DistillationProcedure setDistillationEndReason(DistillationEndReason distillationEndReason) {
        this.distillationEndReason = distillationEndReason;
        return this;
    }
}
