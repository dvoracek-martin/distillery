package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import com.dvoracek.distillery.service.distillation.exchange.data.internal.DistillationExchangeDataDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;

import java.util.Date;

public class DistillationPlanTask implements Runnable {
    private final DistillationPlanEventPublisher distillationPlanEventPublisher;
    private final DistillationExchangeDataService distillationExchangeDataService;

    private DistillationPlanDto distillationPlanDto;


    public DistillationPlanTask(DistillationPlanEventPublisher distillationPlanEventPublisher, DistillationExchangeDataService distillationExchangeDataService, DistillationPlanDto distillationPlanDto) {
        this.distillationPlanEventPublisher = distillationPlanEventPublisher;
        this.distillationExchangeDataService = distillationExchangeDataService;
        this.distillationPlanDto = distillationPlanDto;
    }

    @Override
    public void run() {
        System.out.println(new Date() + " Runnable Task with " + distillationPlanDto.getId()
                + " on thread " + Thread.currentThread().getName());

        // purge the exchangeData values from the previous process Commented for test purposes
        // distillationExchangeDataService.deleteAll();
        long timeStart = System.currentTimeMillis();

        for (DistillationPhaseDto distillationPhaseDto : distillationPlanDto.getDistillationPhases()) {
            DistillationExchangeDataDto distillationExchangeDataDto = distillationExchangeDataService.findFirstByOrderByIdDesc();
            distillationExchangeDataService.setCurrentPlanAndPhaseId(distillationPlanDto.getId(), distillationPhaseDto.getId());
            // TODO implement auto phase start vs wait for confirmation


            double temperatureFromSensors = distillationExchangeDataDto.getTemperature();
            double flowFromSensors = distillationExchangeDataDto.getFlow();
            double weightFromSensors = distillationExchangeDataDto.getWeight();
            // init
            double lastValueFlow = Double.MIN_VALUE;
            double lastValueWeight = Double.MIN_VALUE;
            long phaseTimeInMillis = distillationPhaseDto.getTime() * 1000 * 60;
            boolean wasTurnOn = false;

            while (true) {
                distillationExchangeDataDto = distillationExchangeDataService.findFirstByOrderByIdDesc();
                if (distillationExchangeDataDto.isTerminate()) {
                    // TODO emit phase ended
                    break;
                }
                // if there is a waiting signal, don't do anything, check every second
                final int TICK_INTERVAL = 5000;
                if (distillationExchangeDataDto.isWaiting()) {
                    try {
                        distillationExchangeDataService.setTurnOn(false);
                        wasTurnOn = false;
                        Thread.sleep(TICK_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                if (distillationPhaseDto.getTemperature() != Double.MIN_VALUE) {
                    if ((distillationPhaseDto.getTemperature()) + 5 < temperatureFromSensors) {
                        if (!wasTurnOn) {
                            distillationExchangeDataService.setTurnOn(true);
                            wasTurnOn = true;
                        }
                    } else {
                        if (!wasTurnOn) {
                            distillationExchangeDataService.setTurnOn(false);
                            wasTurnOn = false;
                        }
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

                // if elapsed more time than defined for the phase

                long elapsedTimeInMillis = System.currentTimeMillis() - timeStart;
                if (elapsedTimeInMillis > phaseTimeInMillis) {
                    distillationExchangeDataService.setTurnOn(false);
                    break;
                }

                lastValueFlow = flowFromSensors;
                lastValueWeight = weightFromSensors;
                try {
                    Thread.sleep(TICK_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        distillationPlanEventPublisher.publishDistillationPlanEndEvent(distillationPlanDto);
    }
}

