package com.dvoracek.distillery.service.distillation.measurement.internal;

import com.dvoracek.distillery.domain.measurement.DistillationMeasurement;
import com.dvoracek.distillery.domain.measurement.DistillationMeasurementRepository;
import com.dvoracek.distillery.service.distillation.measurement.DistillationMeasurementService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DefaultDistillationMeasurementService implements DistillationMeasurementService {

    private final DistillationMeasurementRepository distillationMeasurementRepository;

    public DefaultDistillationMeasurementService(DistillationMeasurementRepository distillationMeasurementRepository) {
        this.distillationMeasurementRepository = distillationMeasurementRepository;
    }

    @Override
    public DistillationMeasurementDto createDistillationMeasurement(CreateDistillationMeasurementDto createDistillationMeasurementDto) {
        DistillationMeasurement distillationMeasurement = new DistillationMeasurement();
        distillationMeasurement.setFlow(createDistillationMeasurementDto.getFlow());
        distillationMeasurement.setTemperature(createDistillationMeasurementDto.getTemperature());
        distillationMeasurement.setWeight(createDistillationMeasurementDto.getWeight());
        return DistillationMeasurementDto.toDto(distillationMeasurementRepository.save(distillationMeasurement));
    }

    @Override
    public DistillationMeasurementDto findFirstByOrderByIdDesc() {
        return DistillationMeasurementDto.toDto(distillationMeasurementRepository.findFirstByOrderByIdDesc());
    }

    @Override
    public void deleteAll() {
        distillationMeasurementRepository.deleteAll();
    }

    @Override
    public void setTurnOn(boolean shallTurnOn) {
        getLast().setTurnOn(shallTurnOn);
    }

    @Override
    public void setAlcLevel(double alcLevel) {
        getLast().setAlcLevel(alcLevel);
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        getLast().setWaiting(isWaiting);
    }

    private DistillationMeasurement getLast() {
        return distillationMeasurementRepository.findFirstByOrderByIdDesc();
    }
}
