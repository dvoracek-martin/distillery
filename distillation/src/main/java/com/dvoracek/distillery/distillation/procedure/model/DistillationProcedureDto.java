package com.dvoracek.distillery.distillation.procedure.model;

import java.time.LocalDateTime;

public class DistillationProcedureDto {
    private Long id;
    private Long distillationPlanId;
    private String distillationPlanName;
    private int attemptNumber;
    private LocalDateTime distillationTimeStart;
    private LocalDateTime distillationTimeEnd;
    private DistillationEndReason distillationEndReason;

    public static DistillationProcedureDto toDistillationProcedureDto(DistillationProcedure distillationProcedure) {
        return new DistillationProcedureDto()
                .setId(distillationProcedure.getId())
                .setDistillationPlanId(distillationProcedure.getPlanId())
                .setDistillationPlanName(distillationProcedure.getPlanName())
                .setAttemptNumber(distillationProcedure.getAttemptNumber())
                .setDistillationTimeStart(distillationProcedure.getTimeStart())
                .setDistillationTimeEnd(distillationProcedure.getTimeEnd())
                .setDistillationEndReason(distillationProcedure.getEndReason());

    }

    public static DistillationProcedure fromDistillationProcedureDto(DistillationProcedureDto distillationProcedureDto) {
        return new DistillationProcedure()
                .setId(distillationProcedureDto.getId())
                .setPlanId(distillationProcedureDto.getDistillationPlanId())
                .setPlanName(distillationProcedureDto.getDistillationPlanName())
                .setAttemptNumber(distillationProcedureDto.getAttemptNumber())
                .setTimeStart(distillationProcedureDto.getDistillationTimeStart())
                .setTimeEnd(distillationProcedureDto.getDistillationTimeEnd())
                .setEndReason(distillationProcedureDto.getDistillationEndReason());
    }

    public Long getId() {
        return id;
    }

    public DistillationProcedureDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getDistillationPlanId() {
        return distillationPlanId;
    }

    public DistillationProcedureDto setDistillationPlanId(Long distillationPlanId) {
        this.distillationPlanId = distillationPlanId;
        return this;
    }

    public String getDistillationPlanName() {
        return distillationPlanName;
    }

    public DistillationProcedureDto setDistillationPlanName(String distillationPlanName) {
        this.distillationPlanName = distillationPlanName;
        return this;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public DistillationProcedureDto setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
        return this;
    }

    public LocalDateTime getDistillationTimeStart() {
        return distillationTimeStart;
    }

    public DistillationProcedureDto setDistillationTimeStart(LocalDateTime distillationTimeStart) {
        this.distillationTimeStart = distillationTimeStart;
        return this;
    }

    public LocalDateTime getDistillationTimeEnd() {
        return distillationTimeEnd;
    }

    public DistillationProcedureDto setDistillationTimeEnd(LocalDateTime distillationTimeEnd) {
        this.distillationTimeEnd = distillationTimeEnd;
        return this;
    }

    public DistillationEndReason getDistillationEndReason() {
        return distillationEndReason;
    }

    public DistillationProcedureDto setDistillationEndReason(DistillationEndReason distillationEndReason) {
        this.distillationEndReason = distillationEndReason;
        return this;
    }
}
