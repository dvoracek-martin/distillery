package com.dvoracek.distillery.web.distillation.plan;

import com.dvoracek.distillery.service.distillation.plan.internal.CreateDistillationPlanDto;
import com.dvoracek.distillery.service.distillation.plan.internal.DistillationPlanDto;
import com.dvoracek.distillery.service.distillation.plan.DistillationPlanService;
import com.dvoracek.distillery.service.distillation.plan.internal.UpdateDistillationPlanDto;
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
    public DistillationPlanDto getPhase(@PathVariable("id") Long id) {
        LOGGER.debug("Received Http.GET /api/plan/" + id);
        return this.distillationPlanService.getDistillationPlan(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPlanDto updatePlan(@PathVariable("id") Long id, @RequestBody @Validated UpdateDistillationPlanDto updateDistillationPlanDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.PUT /api/plan : {} with an id: {}", new ObjectMapper().writeValueAsString(updateDistillationPlanDto), id);
        return this.distillationPlanService.updatePlan(id, updateDistillationPlanDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistillationPlanDto createPlan(@RequestBody @Validated CreateDistillationPlanDto createDistillationPlanDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.POST /api/plan : {}", new ObjectMapper().writeValueAsString(createDistillationPlanDto));
        return this.distillationPlanService.createDistillationPlan(createDistillationPlanDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlan(@PathVariable("id") Long id) throws JsonProcessingException {
        LOGGER.debug("Received Delete Request /api/plan : {}", new ObjectMapper().writeValueAsString(id));
        this.distillationPlanService.deleteDistillationPlan(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationPlanDto> getAllPlans() {
        LOGGER.debug("Received Http.GET /api/getAll");
        return this.distillationPlanService.getAll();
    }


    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public void startPlan(@RequestBody @Validated DistillationPlanDto distillationPlanDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.POST /api/plan/start : {}", new ObjectMapper().writeValueAsString("distillationPlanDto"));
        this.distillationPlanService.startDistillation(distillationPlanDto);
    }
}
