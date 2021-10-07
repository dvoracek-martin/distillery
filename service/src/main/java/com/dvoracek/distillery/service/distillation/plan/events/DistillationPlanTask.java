package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.measurement.DistillationMeasurementService;
import com.dvoracek.distillery.service.distillation.measurement.internal.DistillationMeasurementDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import org.apache.tomcat.jni.Time;

import java.util.Date;

public class DistillationPlanTask implements Runnable {
    private final DistillationPlanEventPublisher distillationPlanEventPublisher;
    private final DistillationMeasurementService distillationMeasurementService;

    private DistillationPlanDto distillationPlanDto;


    public DistillationPlanTask(DistillationPlanEventPublisher distillationPlanEventPublisher, DistillationMeasurementService distillationMeasurementService, DistillationPlanDto distillationPlanDto) {
        this.distillationPlanEventPublisher = distillationPlanEventPublisher;
        this.distillationMeasurementService = distillationMeasurementService;
        this.distillationPlanDto = distillationPlanDto;
    }

    @Override
    public void run() {
        System.out.println(new Date() + " Runnable Task with " + distillationPlanDto.getId()
                + " on thread " + Thread.currentThread().getName());

        // purge the measurement values from the previous process
        distillationMeasurementService.deleteAll();

        long timeStart = System.currentTimeMillis();

        for (DistillationPhaseDto distillationPhaseDto : distillationPlanDto.getDistillationPhases()) {
            DistillationMeasurementDto distillationMeasurementDto = distillationMeasurementService.findFirstByOrderByIdDesc();
            // TODO implement auto phase start vs wait for confirmation


            double temperatureFromSensors = distillationMeasurementDto.getTemperature();
            double flowFromSensors = distillationMeasurementDto.getFlow();
            double weightFromSensors = distillationMeasurementDto.getWeight();
            // init
            double lastValueFlow = Double.MIN_VALUE;
            double lastValueWeight = Double.MIN_VALUE;

            while (true) {
                if (distillationMeasurementDto.isTerminate()) {
                    // TODO emit phase ended
                    break;
                }
                // if there is a pause don't do anything, check every second
                if (distillationMeasurementDto.isPause()) {
                    // TODO emit waiting
                    Time.sleep(1);
                    continue;
                }

                if (distillationPhaseDto.getTemperature() != Double.MIN_VALUE) {
                    if ((distillationPhaseDto.getTemperature()) + 5 < temperatureFromSensors) {
                        distillationMeasurementService.setTurnOn(true);
                    } else {
                        distillationMeasurementService.setTurnOn(true);
                    }
                }
                if (distillationPhaseDto.getFlow() != Double.MIN_VALUE) {
                    // TODO
                    // if max volume pro phase then
                }
                if (distillationPhaseDto.getVolume() != Double.MIN_VALUE && lastValueFlow != Double.MIN_VALUE && lastValueWeight != Double.MIN_VALUE) {
                    // What is the current alc. level
                    // The formula is (Volume - Weight)/(0.2107 * Volume) = percentage of ethanol
                    double flow = flowFromSensors - lastValueFlow;
                    double weight = weightFromSensors - lastValueWeight;
                    double alcLevel = (flow - weight) / (0.2107 * flow);
                    // TODO emit info
                }
                // TODO do I need it?
//                if (distillationPhaseDto.getVolume() != null) {
//                    if ((System.currentTimeMillis() - timeStart) > distillationPhaseDto.getTime()) {
//                        break;
//                    }
//                }

                // if passed more time than defined for the phase
                if ((System.currentTimeMillis() - timeStart) > distillationPhaseDto.getTime()) {
                    // TODO
                    break;
                }

                lastValueFlow = flowFromSensors;
                lastValueWeight = weightFromSensors;
                Time.sleep(10);
            }
        }


        distillationPlanEventPublisher.publishDistillationPlanEndEvent(distillationPlanDto);
    }
}

