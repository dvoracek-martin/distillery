package com.dvoracek.distillery.service.distillation.measurement;

import com.dvoracek.distillery.service.distillation.measurement.internal.CreateDistillationMeasurementDto;
import com.dvoracek.distillery.service.distillation.measurement.internal.DistillationMeasurementDto;

public interface DistillationMeasurementService {

    DistillationMeasurementDto createDistillationMeasurement(CreateDistillationMeasurementDto createDistillationMeasurementDto);

    DistillationMeasurementDto findFirstByOrderByIdDesc();

    void deleteAll();

    void setTurnOn(boolean shallTurnOn);

    void setAlcLevel(double alcLevel);

    void setWaiting(boolean isWaiting);
}
