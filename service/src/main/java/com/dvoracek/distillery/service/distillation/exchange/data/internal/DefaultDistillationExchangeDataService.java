package com.dvoracek.distillery.service.distillation.exchange.data.internal;

import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeData;
import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeDataRepository;
import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DefaultDistillationExchangeDataService implements DistillationExchangeDataService {

    private final DistillationExchangeDataRepository distillationExchangeDataRepository;

    public DefaultDistillationExchangeDataService(DistillationExchangeDataRepository distillationExchangeDataRepository) {
        this.distillationExchangeDataRepository = distillationExchangeDataRepository;
    }

    @Override
    public DistillationExchangeDataDto createDistillationExchangeData(CreateDistillationExchangeDataDto createDistillationExchangeDataDto) {
        DistillationExchangeData distillationExchangeData = new DistillationExchangeData();
        distillationExchangeData.setFlow(createDistillationExchangeDataDto.getFlow());
        distillationExchangeData.setTemperature(createDistillationExchangeDataDto.getTemperature());
        distillationExchangeData.setWeight(createDistillationExchangeDataDto.getWeight());
        distillationExchangeData.setWaiting(createDistillationExchangeDataDto.isWaiting());
        distillationExchangeData.setTurnOn(createDistillationExchangeDataDto.isTurnOn());
        distillationExchangeData.setTerminate(createDistillationExchangeDataDto.isTerminate());
        distillationExchangeData.setPlanId(createDistillationExchangeDataDto.getPlanId());
        distillationExchangeData.setCurrentPhaseId(createDistillationExchangeDataDto.getCurrentPhaseId());
        return DistillationExchangeDataDto.toDto(distillationExchangeDataRepository.save(distillationExchangeData));
    }

    @Override
    public DistillationExchangeDataDto findFirstByOrderByIdDesc() {
        DistillationExchangeData distillationExchangeData = null;
        boolean isWaiting = false;
        while (distillationExchangeData == null) {
            try {
                if (isWaiting) {
                    System.out.println("WATING LAST");
                }
                isWaiting = true;
                distillationExchangeData = distillationExchangeDataRepository.findTopByOrderByIdDesc();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return DistillationExchangeDataDto.toDto(distillationExchangeData);
    }

    @Override
    public void deleteAll() {
        distillationExchangeDataRepository.deleteAll();
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

    @Override
    public void setCurrentPlanAndPhaseIdAndNotTerminate(Long distillationPlanId, Long distillationPhaseId, boolean terminate) {
        DistillationExchangeData distillationExchangeData = getLast();
        if (distillationExchangeData == null) {
            distillationExchangeData = new DistillationExchangeData();
        }
        distillationExchangeData.setPlanId(distillationPlanId);
        distillationExchangeData.setCurrentPhaseId(distillationPhaseId);
        distillationExchangeData.setTerminate(false);
        distillationExchangeDataRepository.save(distillationExchangeData);
    }

    @Override
    public void updateTimeLeft(long elapsedTimeInMillis) {
        getLast().setTimeElapsed(elapsedTimeInMillis);
    }

    private DistillationExchangeData getLast() {
        return distillationExchangeDataRepository.findTopByOrderByIdDesc();
    }
}
