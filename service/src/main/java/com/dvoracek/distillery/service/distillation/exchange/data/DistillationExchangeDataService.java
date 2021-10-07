package com.dvoracek.distillery.service.distillation.exchange.data;

import com.dvoracek.distillery.service.distillation.exchange.data.internal.CreateDistillationExchangeDataDto;
import com.dvoracek.distillery.service.distillation.exchange.data.internal.DistillationExchangeDataDto;

public interface DistillationExchangeDataService {

    DistillationExchangeDataDto createDistillationExchangeData(CreateDistillationExchangeDataDto createDistillationExchangeDataDto);

    DistillationExchangeDataDto findFirstByOrderByIdDesc();

    void deleteAll();

    void setTurnOn(boolean shallTurnOn);

    void setAlcLevel(double alcLevel);

    void setWaiting(boolean isWaiting);

    void setCurrentPlanAndPhaseId(Long distillationPlanId, Long distillationPhaseId);
}
