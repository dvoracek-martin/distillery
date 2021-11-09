package com.dvoracek.distillery.service.distillation.plan.events;

import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import com.dvoracek.distillery.service.distillation.exchange.data.internal.DistillationExchangeDataDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DefaultDistillationPhaseService;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DistillationPlanTask implements ApplicationListener<DistillationPlanEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPhaseService.class);
    private static final int TICK_INTERVAL = 1000;
    private static final int HEAT_TOLERANCE_IN_CELSIUS = 3;

    private final DistillationPlanEventPublisher distillationPlanEventPublisher;
    private final DistillationExchangeDataService distillationExchangeDataService;

    private static DistillationPlanDto distillationPlanDto;

    private static boolean shallReloadPlan = false;
    private boolean nextPhase;


    public DistillationPlanTask(DistillationPlanEventPublisher distillationPlanEventPublisher, DistillationExchangeDataService distillationExchangeDataService) {
        this.distillationPlanEventPublisher = distillationPlanEventPublisher;
        this.distillationExchangeDataService = distillationExchangeDataService;
    }

    public void startDistillation() {

        // purge the exchangeData values from the previous process
        distillationExchangeDataService.deleteAll();

        // init
        boolean initialized = false;
        int index = 0;
        for (DistillationPhaseDto distillationPhaseDto : this.distillationPlanDto.getDistillationPhases()) {
            // TODO implement auto phase start vs wait for confirmation
            if (!initialized) {
                distillationExchangeDataService.setCurrentPlanAndPhaseIdAndNotTerminate(this.distillationPlanDto.getId(), distillationPhaseDto.getId(), false);
                initialized = true;
            }

            long timeStart = System.currentTimeMillis();
            DistillationExchangeDataDto distillationExchangeDataDto = distillationExchangeDataService.findFirstByOrderByIdDesc();
            double flowFromSensors = distillationExchangeDataDto.getFlow();
            double weightFromSensors = distillationExchangeDataDto.getWeight();
            // init
            long phaseTimeInMillis = distillationPhaseDto.getTime() * 1000 * 60;
            long waitingTime = 0;
            long waitingTimeInTotal = 0;
            boolean isWaiting = false;

            while (true) {
                if (nextPhase) {
                    nextPhase = false;
                    initialized = false;
                    break;
                }

                // if the current distillation phase has been updated while the distillation plan is running
                if (shallReloadPlan) {
                    distillationPhaseDto = this.distillationPlanDto.getDistillationPhases().get(index);

                    // if the current distillation phase has just been deleted
                    if (distillationPhaseDto == null) {
                        initialized = false;
                        break;
                    }
                    phaseTimeInMillis = distillationPhaseDto.getTime() * 1000 * 60;
                    shallReloadPlan = false;
                }

                distillationExchangeDataDto = distillationExchangeDataService.findFirstByOrderByIdDesc();

                if (distillationExchangeDataDto.isTerminate()) {
                    distillationExchangeDataService.finishDistillation();
                    distillationPlanEventPublisher.publishDistillationPlanEndEvent(distillationPlanDto);
                    break;
                }

                // if there is a waiting signal, don't do anything, check every second
                if (distillationExchangeDataDto.isWaiting()) {
                    try {
                        if (!isWaiting) {
                            waitingTime = System.currentTimeMillis();
                            isWaiting = true;
                            distillationExchangeDataService.setTurnOn(false);
                        }
                        Thread.sleep(TICK_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    if (isWaiting) {
                        waitingTimeInTotal += System.currentTimeMillis() - waitingTime;
                        waitingTime = 0;
                        isWaiting = false;
                        distillationExchangeDataService.setTurnOn(true);
                    }
                }

                // measure the temperature and turn the heater on/off
                if ((distillationPhaseDto.getTemperature()) > distillationExchangeDataDto.getTemperature()) {
                    distillationExchangeDataService.setTurnOn(true);
                } else if ((distillationPhaseDto.getTemperature()) + HEAT_TOLERANCE_IN_CELSIUS < distillationExchangeDataDto.getTemperature()) {
                    distillationExchangeDataService.setTurnOn(false);
                }

                // TODO measure flow & weight

                // if has elapsed more time than defined for the phase, switch to the next phase
                long elapsedTimeInMillis = System.currentTimeMillis() - timeStart - waitingTimeInTotal;
                distillationExchangeDataService.updateTimeLeft(elapsedTimeInMillis);
                if (elapsedTimeInMillis > phaseTimeInMillis) {
                    distillationExchangeDataService.setTurnOn(false);
                    initialized = false;
                    break;
                }

                try {
                    Thread.sleep(TICK_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            index++;
        }
        distillationExchangeDataService.finishDistillation();
        distillationPlanEventPublisher.publishDistillationPlanEndEvent(distillationPlanDto);
    }

    @Override
    public void onApplicationEvent(DistillationPlanEvent event) {
        if (event instanceof DistillationPlanStartEvent) {
            this.startDistillation();
            LOGGER.info("Start of a distillation plan");
        } else if (event instanceof DistillationPlanEndEvent) {
            distillationExchangeDataService.finishDistillation();
            LOGGER.info("End of a distillation plan");
        } else if (event instanceof DistillationPlanUpdatedEvent) {
            this.distillationPlanDto = event.getDistillationPlanDto();
            shallReloadPlan = true;
        } else if (event instanceof DistillationPlanJumpToNextPhase) {
            nextPhase = true;
        }
    }

    public DistillationPlanTask setDistillationPlanDto(DistillationPlanDto distillationPlanDto) {
        this.distillationPlanDto = distillationPlanDto;
        return this;
    }
}
