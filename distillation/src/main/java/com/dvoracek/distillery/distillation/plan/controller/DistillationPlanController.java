package com.dvoracek.distillery.distillation.plan.controller;

import com.dvoracek.distillery.distillation.plan.service.DistillationPlanService;
import com.dvoracek.distillery.distillation.plan.service.internal.CreateDistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.DistillationPlanDto;
import com.dvoracek.distillery.distillation.plan.service.internal.EditDistillationPlanDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/plan")
@CrossOrigin(origins = "http://localhost:4200")
public class DistillationPlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistillationPlanController.class);

    private final DistillationPlanService distillationPlanService;

    public DistillationPlanController(DistillationPlanService distillationPlanService) {
        this.distillationPlanService = distillationPlanService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPlanDto getPlan(@PathVariable("id") Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Received Http.GET /api/plan/ %d", id));
        }
        return DistillationPlanDto.toDistillationPlanDto(this.distillationPlanService.getDistillationPlan(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPlanDto editPlan(@PathVariable("id") Long id, @RequestBody @Validated EditDistillationPlanDto editDistillationPlanDto) throws JsonProcessingException {
        if (LOGGER.isDebugEnabled()) {
            String editDistillationPlanAsString = new ObjectMapper().writeValueAsString(editDistillationPlanDto);

            LOGGER.debug(String.format("Received Http.PUT /api/plan : %s with an id: %d", editDistillationPlanAsString, id));
        }
        return DistillationPlanDto.toDistillationPlanDto(this.distillationPlanService.editPlan(id, editDistillationPlanDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistillationPlanDto createPlan(@RequestBody @Validated CreateDistillationPlanDto createDistillationPlanDto) throws JsonProcessingException {
        if (LOGGER.isDebugEnabled()) {
            String editDistillationPlanAsString = new ObjectMapper().writeValueAsString(createDistillationPlanDto);
            LOGGER.debug(String.format("Received Http.POST /api/plan : %s", editDistillationPlanAsString));
        }
        return DistillationPlanDto.toDistillationPlanDto(this.distillationPlanService.createDistillationPlan(createDistillationPlanDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlan(@PathVariable("id") Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Received Delete Request /api/plan : %d", id));
        }
        this.distillationPlanService.deleteDistillationPlan(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationPlanDto> getAllPlans() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Received Http.GET /api/getAll");
        }
        return this.distillationPlanService.getAll().stream().map(DistillationPlanDto::toDistillationPlanDto).toList();
    }


    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public void startPlan(@RequestBody @Validated DistillationPlanDto distillationPlanDto) throws JsonProcessingException {
        if (LOGGER.isDebugEnabled()) {
            String distillationPlanDtoAsString = new ObjectMapper().writeValueAsString("distillationPlanDto");
            LOGGER.debug(String.format("Received Http.POST /api/plan/start : %s", distillationPlanDtoAsString));
        }
        this.distillationPlanService.startDistillation(DistillationPlanDto.fromDistillationPlanDto(distillationPlanDto));
    }

    @PostMapping("/terminate")
    @ResponseStatus(HttpStatus.OK)
    public void terminatePlan(@RequestBody @Validated DistillationPlanDto distillationPlanDto) throws JsonProcessingException {
        if (LOGGER.isDebugEnabled()) {
            String distillationPlanDtoAsString = new ObjectMapper().writeValueAsString("distillationPlanDto");
            LOGGER.debug(String.format("Received Http.POST /api/plan/terminate : %s", distillationPlanDtoAsString));
        }
        this.distillationPlanService.terminateDistillation(DistillationPlanDto.fromDistillationPlanDto(distillationPlanDto));
    }

    @PostMapping("/next")
    @ResponseStatus(HttpStatus.OK)
    public void jumpToNextPhase(@RequestBody @Validated DistillationPlanDto distillationPlanDto) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Received Http.POST /api/phase/post");
        }
        this.distillationPlanService.jumpToNextPhase(DistillationPlanDto.fromDistillationPlanDto(distillationPlanDto));
    }
}
