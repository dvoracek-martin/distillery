package com.dvoracek.distillery.distillation.process.service;

import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataFromRaspiDto;
import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataToFrontendDto;

public interface DistillationProcessService {
    void startDistillation(long planId);

    void updateDistillationProcessData(DistillationProcessDataFromRaspiDto distillationProcessDataFromRaspiDto);

    void nextPhase();

    void terminateProcess(long planId);
    void terminateProcessByUser(long planId);

    DistillationProcessDataToFrontendDto getDataForFrontend();

    void shallReloadDistillationPlan(boolean isDistillationPlanDirty);

    void logBackendProgress(String dataFromBackend);
}
