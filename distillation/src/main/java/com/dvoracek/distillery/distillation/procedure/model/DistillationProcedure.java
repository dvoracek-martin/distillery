package com.dvoracek.distillery.distillation.procedure.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_DISTILLATION_PROCEDURE")
public class DistillationProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Column(name = "plan_id")
    private Long planId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "plan_name")
    private String planName;

    @NotNull
    @Column(name = "attempt_number")
    private int attemptNumber;

    @NotNull
    @Column(name = "time_start")
    private LocalDateTime timeStart;

    @Column(name = "time_end")
    private LocalDateTime timeEnd;

    @NotNull
    @Column(name = "end_reason")
    private DistillationEndReason endReason;

    public Long getId() {
        return id;
    }

    public DistillationProcedure setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public DistillationProcedure setPlanId(Long distillationPlanId) {
        this.planId = distillationPlanId;
        return this;
    }

    public String getPlanName() {
        return planName;
    }

    public DistillationProcedure setPlanName(String distillationPlanName) {
        this.planName = distillationPlanName;
        return this;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public DistillationProcedure setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
        return this;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public DistillationProcedure setTimeStart(LocalDateTime distillationTimeStart) {
        this.timeStart = distillationTimeStart;
        return this;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public DistillationProcedure setTimeEnd(LocalDateTime distillationTimeEnd) {
        this.timeEnd = distillationTimeEnd;
        return this;
    }

    public DistillationEndReason getEndReason() {
        return endReason;
    }

    public DistillationProcedure setEndReason(DistillationEndReason distillationEndReason) {
        this.endReason = distillationEndReason;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistillationProcedure that)) return false;

        if (attemptNumber != that.attemptNumber) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (planId != null ? !planId.equals(that.planId) : that.planId != null) return false;
        if (planName != null ? !planName.equals(that.planName) : that.planName != null) return false;
        if (timeStart != null ? !timeStart.equals(that.timeStart) : that.timeStart != null) return false;
        if (timeEnd != null ? !timeEnd.equals(that.timeEnd) : that.timeEnd != null) return false;
        return endReason == that.endReason;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (planId != null ? planId.hashCode() : 0);
        result = 31 * result + (planName != null ? planName.hashCode() : 0);
        result = 31 * result + attemptNumber;
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeEnd != null ? timeEnd.hashCode() : 0);
        result = 31 * result + (endReason != null ? endReason.hashCode() : 0);
        return result;
    }
}
