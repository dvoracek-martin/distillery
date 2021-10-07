package com.dvoracek.distillery.service.distillation.plan.internal;

import com.dvoracek.distillery.domain.exchange.data.DistillationExchangeDataRepository;
import com.dvoracek.distillery.domain.phase.DistillationPhase;
import com.dvoracek.distillery.domain.phase.DistillationPhaseRepository;
import com.dvoracek.distillery.domain.plan.DistillationPlan;
import com.dvoracek.distillery.domain.plan.DistillationPlanRepository;
import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import com.dvoracek.distillery.service.distillation.phase.internal.CreateDistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseNotFoundException;
import com.dvoracek.distillery.service.distillation.phase.internal.UpdateDistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.plan.*;
import com.dvoracek.distillery.service.distillation.plan.events.DistillationPlanEventPublisher;
import com.dvoracek.distillery.service.distillation.plan.events.DistillationPlanTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultDistillationPlanService implements DistillationPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationPlanService.class);

    private final DistillationPlanRepository distillationPlanRepository;
    private final DistillationPhaseRepository distillationPhaseRepository;
    private final DistillationPlanEventPublisher distillationPlanEventPublisher;
    private final DistillationExchangeDataService distillationExchangeDataService;
    private final TaskScheduler taskScheduler;

    public DefaultDistillationPlanService(DistillationPlanRepository distillationPlanRepository, DistillationPhaseRepository distillationPhaseRepository, DistillationPlanEventPublisher distillationPlanEventPublisher, DistillationExchangeDataRepository distillationExchangeDataRepository, DistillationExchangeDataService distillationExchangeDataService, TaskScheduler taskScheduler) {
        this.distillationPlanRepository = distillationPlanRepository;
        this.distillationPhaseRepository = distillationPhaseRepository;
        this.distillationPlanEventPublisher = distillationPlanEventPublisher;
        this.distillationExchangeDataService = distillationExchangeDataService;
        this.taskScheduler = taskScheduler;
    }

    public List<DistillationPlanDto> getAll() {
        return distillationPlanRepository.findAll().stream()
                .map(DistillationPlanDto::toDistillationPlanDto).collect(Collectors.toList());
    }

    @Override
    public DistillationPlanDto updatePlan(Long id, UpdateDistillationPlanDto updateDistillationPlanDto) {
        DistillationPlan distillationPlan = findById(id);
        distillationPlan.setName(updateDistillationPlanDto.getName());
        distillationPlan.setDescription(updateDistillationPlanDto.getDescription());
        List<DistillationPhase> phases = new ArrayList<>();
        for (UpdateDistillationPhaseDto updateDistillationPhaseDto : updateDistillationPlanDto.getDistillationPhases()) {
            // create a phase if it doesn't exist yet
            if (updateDistillationPhaseDto.getId() == null) {
                createNewPhase(distillationPlan, updateDistillationPhaseDto.getName(), updateDistillationPhaseDto.getTemperature(), updateDistillationPhaseDto.getFlow(), updateDistillationPhaseDto.getVolume(), updateDistillationPhaseDto.getTime());
            } else {
                DistillationPhase distillationPhase = distillationPhaseRepository.findById(updateDistillationPhaseDto.getId()).orElseThrow(() -> new DistillationPhaseNotFoundException(id));
                distillationPhase.setName(updateDistillationPhaseDto.getName());
                distillationPhase.setWeight(updateDistillationPhaseDto.getVolume());
                distillationPhase.setFlow(updateDistillationPhaseDto.getFlow());
                distillationPhase.setTemperature(updateDistillationPhaseDto.getTemperature());
                distillationPhase.setTime(updateDistillationPhaseDto.getTime());
                distillationPhase.setPlan(distillationPlan);
                phases.add(distillationPhase);
            }
        }
        distillationPlan.setDistillationPhases(phases);
        LOGGER.info("Phase updated. ID: {}, name: {}", distillationPlan.getId(), distillationPlan.getName());
        return DistillationPlanDto.toDistillationPlanDto(distillationPlan);
    }

    public DistillationPlanDto getDistillationPlan(Long id) {
        return DistillationPlanDto.toDistillationPlanDto((distillationPlanRepository.findById(id)).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    public DistillationPlanDto createDistillationPlan(CreateDistillationPlanDto createDistillationPlanDto) {
        DistillationPlan distillationPlan = new DistillationPlan();
        distillationPlan.setName(createDistillationPlanDto.getName());
        distillationPlan.setDescription(createDistillationPlanDto.getDescription());
        distillationPlan = distillationPlanRepository.save(distillationPlan);
        if (!createDistillationPlanDto.getDistillationPhases().isEmpty()) {
            for (CreateDistillationPhaseDto createDistillationPhaseDto : createDistillationPlanDto.getDistillationPhases()) {
                createNewPhase(distillationPlan, createDistillationPhaseDto.getName(), createDistillationPhaseDto.getTemperature(), createDistillationPhaseDto.getFlow(), createDistillationPhaseDto.getVolume(), createDistillationPhaseDto.getTime());
            }
        }
        return DistillationPlanDto.toDistillationPlanDto(distillationPlan);
    }

    @Override
    public void deleteDistillationPlan(Long id) {
        distillationPlanRepository.delete(distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id)));
    }

    @Override
    public void startDistillation(DistillationPlanDto distillationPlanDto) {
        Object[] objects = getRunningTasks();
        if (objects.length == 0) {
            DistillationPlanTask distillationPlanTask = new DistillationPlanTask(distillationPlanEventPublisher, distillationExchangeDataService, distillationPlanDto);
            taskScheduler.schedule(distillationPlanTask, new Date());
            distillationPlanEventPublisher.publishDistillationPlanStartEvent(distillationPlanDto);
        } else {
            System.out.println("Trying to start, when there is already running");
        }
    }

    private Object[] getRunningTasks() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = (ThreadPoolTaskScheduler) this.taskScheduler;
        ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor) threadPoolTaskScheduler.getScheduledExecutor();
        BlockingQueue<Runnable> queue = scheduledExecutor.getQueue();
        return queue.toArray();
    }


    private DistillationPlan findById(Long id) {
        return distillationPlanRepository.findById(id).orElseThrow(() -> new DistillationPlanNotFoundException(id));
    }

    private void createNewPhase(DistillationPlan distillationPlan, String name, double temperature, double flow, double volume, Long time) {
        DistillationPhase distillationPhase = new DistillationPhase();
        distillationPhase.setPlan(distillationPlan);
        distillationPhase.setName(name);
        distillationPhase.setTemperature(temperature);
        distillationPhase.setFlow(flow);
        distillationPhase.setWeight(volume);
        distillationPhase.setTime(time);
        distillationPhaseRepository.save(distillationPhase);
    }
}
