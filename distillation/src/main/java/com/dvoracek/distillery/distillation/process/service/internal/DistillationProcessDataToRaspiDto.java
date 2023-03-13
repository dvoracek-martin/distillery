package com.dvoracek.distillery.distillation.process.service.internal;

public class DistillationProcessDataToRaspiDto {

    private long timeStartedInMillis;
    private long distillationProcedureId;

    public long getTimeStartedInMillis() {
        return timeStartedInMillis;
    }

    public DistillationProcessDataToRaspiDto setTimeStartedInMillis(long timeStartedInMillis) {
        this.timeStartedInMillis = timeStartedInMillis;
        return this;
    }

    public long getDistillationProcedureId() {
        return distillationProcedureId;
    }

    public DistillationProcessDataToRaspiDto setDistillationProcedureId(long distillationProcedureId) {
        this.distillationProcedureId = distillationProcedureId;
        return this;
    }
}
