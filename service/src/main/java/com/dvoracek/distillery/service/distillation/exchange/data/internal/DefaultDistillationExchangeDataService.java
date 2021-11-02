package com.dvoracek.distillery.service.distillation.exchange.data.internal;

import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeData;
import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeDataRepository;
import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

import static com.dvoracek.distillery.service.distillation.plan.events.DistillationPlanEventPublisher.distillationLock;

@Service
@Transactional
public class DefaultDistillationExchangeDataService implements DistillationExchangeDataService {

    private final DistillationExchangeDataRepository distillationExchangeDataRepository;

    public DefaultDistillationExchangeDataService(DistillationExchangeDataRepository distillationExchangeDataRepository) {
        this.distillationExchangeDataRepository = distillationExchangeDataRepository;
    }

    @Override
    public DistillationExchangeDataDto createDistillationExchangeData(CreateDistillationExchangeDataDto createDistillationExchangeDataDto) {
        DistillationExchangeData lastDistillationExchangeData = getLast();
        DistillationExchangeData distillationExchangeData = new DistillationExchangeData();

        // if the data in the databases is newer then the current data, adjust only the measured values
        if (lastDistillationExchangeData.getTimestamp() > createDistillationExchangeDataDto.getTimestamp() || createDistillationExchangeDataDto.getSource().equals("raspi")) {
            distillationExchangeData.setFlow(createDistillationExchangeDataDto.getFlow());
            distillationExchangeData.setTemperature(createDistillationExchangeDataDto.getTemperature());
            distillationExchangeData.setWeight(createDistillationExchangeDataDto.getWeight());
            distillationExchangeData.setWaiting(lastDistillationExchangeData.isWaiting());
            distillationExchangeData.setTurnOn(lastDistillationExchangeData.isTurnOn());
            distillationExchangeData.setTerminate(lastDistillationExchangeData.isTerminate());
            distillationExchangeData.setPlanId(lastDistillationExchangeData.getPlanId());
            distillationExchangeData.setCurrentPhaseId(lastDistillationExchangeData.getCurrentPhaseId());
            distillationExchangeData.setSource(createDistillationExchangeDataDto.getSource());
        } else {
            distillationExchangeData.setFlow(createDistillationExchangeDataDto.getFlow());
            distillationExchangeData.setTemperature(createDistillationExchangeDataDto.getTemperature());
            distillationExchangeData.setWeight(createDistillationExchangeDataDto.getWeight());
            distillationExchangeData.setWaiting(createDistillationExchangeDataDto.isWaiting());
            distillationExchangeData.setTurnOn(createDistillationExchangeDataDto.isTurnOn());
            distillationExchangeData.setTerminate(createDistillationExchangeDataDto.isTerminate());
            distillationExchangeData.setPlanId(createDistillationExchangeDataDto.getPlanId());
            distillationExchangeData.setCurrentPhaseId(createDistillationExchangeDataDto.getCurrentPhaseId());
            distillationExchangeData.setTimeElapsed(createDistillationExchangeDataDto.getTimeElapsed());
            distillationExchangeData.setSource(createDistillationExchangeDataDto.getSource());
        }
        distillationExchangeData.setTimestamp(Instant.now().toEpochMilli());
        return DistillationExchangeDataDto.toDto(distillationExchangeDataRepository.save(distillationExchangeData));
    }

    @Override
    public DistillationExchangeDataDto findFirstByOrderByIdDesc() {
        DistillationExchangeData distillationExchangeData;
        if (distillationExchangeDataRepository.countExchangeData() == 0) {
            return null;
        }
        distillationExchangeData = distillationExchangeDataRepository.findFirstByOrderByCreatedTsByDesc().get(0);
        return DistillationExchangeDataDto.toDto(distillationExchangeData);
    }

    @Override
    public void deleteAll() {
        distillationExchangeDataRepository.deleteAll();
    }

    @Override
    public void setTurnOn(boolean shallTurnOn) {
        getLast().setTurnOn(shallTurnOn).setTimestamp(Instant.now().toEpochMilli());
    }

    @Override
    public void setAlcLevel(double alcLevel) {
        getLast().setAlcLevel(alcLevel).setTimestamp(Instant.now().toEpochMilli());
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        getLast().setWaiting(isWaiting).setTimestamp(Instant.now().toEpochMilli());
    }

    @Override
    public void setCurrentPlanAndPhaseIdAndNotTerminate(Long distillationPlanId, Long distillationPhaseId, boolean terminate) {
        DistillationExchangeData distillationExchangeData = getLast();
        if (distillationExchangeData == null) {
            distillationExchangeData = new DistillationExchangeData();
        }
        distillationExchangeData.setPlanId(distillationPlanId);
        distillationExchangeData.setCurrentPhaseId(distillationPhaseId);
        distillationExchangeData.setSource("backend");
        distillationExchangeData.setTerminate(false);
        distillationExchangeDataRepository.save(distillationExchangeData);
    }

    @Override
    public void updateTimeLeft(long elapsedTimeInMillis) {
        getLast().setTimeElapsed(elapsedTimeInMillis).setTimestamp(Instant.now().toEpochMilli());
    }

    @Override
    public void setTerminated(boolean isTerminated) {
        getLast().setTerminate(isTerminated).setTimestamp(Instant.now().toEpochMilli());
        distillationLock = false;
    }

    @Override
    public void finishDistillation() {
        getLast()
                .setTurnOn(false)
                .setWaiting(false)
                .setTerminate(true)
                .setTimestamp(Instant.now().toEpochMilli());
        distillationLock = false;
        distillationExchangeDataRepository.deleteAll();
    }

    private DistillationExchangeData getLast() {
        if (distillationExchangeDataRepository.countExchangeData() == 0) {
            return null;
        }
        DistillationExchangeData distillationExchangeData = distillationExchangeDataRepository.findFirstByOrderByCreatedTsByDesc().get(0);
        distillationExchangeData.setSource("backend");
        return distillationExchangeData;
    }
}
