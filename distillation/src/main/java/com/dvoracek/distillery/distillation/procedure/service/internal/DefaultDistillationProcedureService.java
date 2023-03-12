package com.dvoracek.distillery.distillation.procedure.service.internal;

import com.dvoracek.distillery.distillation.plan.service.DistillationPlanService;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;
import com.dvoracek.distillery.distillation.procedure.model.DistillationEndReason;
import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;
import com.dvoracek.distillery.distillation.procedure.repository.DistillationProcedureRepository;
import com.dvoracek.distillery.distillation.procedure.service.DistillationProcedureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class DefaultDistillationProcedureService implements DistillationProcedureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationProcedureService.class);
    private final DistillationProcedureRepository distillationProcedureRepository;
    private final DistillationPlanService distillationPlanService;

    public DefaultDistillationProcedureService(DistillationProcedureRepository distillationProcedureRepository, DistillationPlanService distillationPlanService) {
        this.distillationProcedureRepository = distillationProcedureRepository;
        this.distillationPlanService = distillationPlanService;
    }

    @Override
    public DistillationProcedure getDistillationProcedure(Long id) {
        return distillationProcedureRepository.findById(id).orElseThrow(() -> new DistillationProcedureNotFoundException(id));
    }

    @Override
    public DistillationProcedure getByPlanIdAndAttemptNumber(Long planId, int attemptNumber) {
        return distillationProcedureRepository.findByPlanIdAndAttemptNumber(planId, attemptNumber);
    }

    @Override
    public DistillationProcedure getLastByPlan(Long planId) {
        return distillationProcedureRepository.findLastByPlan(planId);
    }

    @Override
    public List<DistillationProcedure> getAll() {
        return distillationProcedureRepository.findAll().stream().sorted(comparing(DistillationProcedure::getId)).collect(Collectors.toList());
    }

    @Override
    public DistillationProcedure createDistillationProcedure(Long planId) {
        DistillationProcedure distillationProcedure = getLastByPlan(planId);
        DistillationProcedure currentDistillationProcedure = new DistillationProcedure();
        DistillationPlanDto distillationPlanDto = distillationPlanService.getDistillationPlan(planId);
        if (distillationProcedure == null) {
            distillationProcedure = new DistillationProcedure()
                    .setPlanId(distillationPlanDto.getId())
                    .setPlanName(distillationPlanDto.getName())
                    .setTimeStart(LocalDateTime.now())
                    .setAttemptNumber(1)
                    .setEndReason(DistillationEndReason.NOT_DONE);
            currentDistillationProcedure = distillationProcedure;
        } else {
            currentDistillationProcedure
                    .setPlanId(distillationPlanDto.getId())
                    .setPlanName(distillationPlanDto.getName())
                    .setTimeStart(LocalDateTime.now())
                    .setAttemptNumber(distillationProcedure.getAttemptNumber() + 1)
                    .setEndReason(DistillationEndReason.NOT_DONE);
        }
        LOGGER.info("Creating a new distillation procedure for plan ID: " + currentDistillationProcedure.getPlanId() + ", attempt #" + currentDistillationProcedure.getAttemptNumber());
        return distillationProcedureRepository.saveAndFlush(currentDistillationProcedure);
    }

    private DistillationProcedure getDistillationProcedureByPlanId(Long planId) {
        return distillationProcedureRepository.findByPlanId(planId);
    }

    @Override
    public DistillationProcedure terminateDistillationProcedure(Long procedureId) {
        DistillationProcedure distillationProcedure = getDistillationProcedure(procedureId);
        distillationProcedure.setTimeEnd(LocalDateTime.now());
        distillationProcedure.setEndReason(DistillationEndReason.DONE);
        return distillationProcedureRepository.saveAndFlush(distillationProcedure);
    }

    @Override
    public DistillationProcedure deleteDistillationProcedure(Long id) {
        DistillationProcedure distillationProcedure = distillationProcedureRepository.findById(id).orElseThrow(() -> new DistillationProcedureNotFoundException(id));
        distillationProcedureRepository.delete(distillationProcedure);
        // TODO delete entries from ES
        LOGGER.info("Procedure deleted. ID: {}, plan Id: {}, attempt #: {}", distillationProcedure.getId(), distillationProcedure.getPlanId(), distillationProcedure.getAttemptNumber());
        return null;
    }
}
