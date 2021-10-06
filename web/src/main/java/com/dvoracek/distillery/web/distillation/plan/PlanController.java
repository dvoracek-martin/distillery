package com.dvoracek.distillery.web.distillation.plan;

import com.dvoracek.distillery.service.distillation.plan.CreateDistillationPlanDto;
import com.dvoracek.distillery.service.distillation.plan.DistillationPlanDto;
import com.dvoracek.distillery.service.distillation.plan.DistillationPlanService;
import com.dvoracek.distillery.service.distillation.plan.UpdateDistillationPlanDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/plans")
@CrossOrigin(origins = "http://localhost:4200")
public class PlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

    private final DistillationPlanService distillationPlanService;

    public PlanController(DistillationPlanService distillationPlanService) {
        this.distillationPlanService = distillationPlanService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPlanDto getPhase(@PathVariable("id") Long id) {
        LOGGER.debug("Received Http.GET /api/plans/" + id);
        return this.distillationPlanService.getDistillationPlan(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPlanDto updatePlan(@PathVariable("id") Long id, @RequestBody @Validated UpdateDistillationPlanDto updateDistillationPlanDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.PUT /api/plans : {} with an id: {}", new ObjectMapper().writeValueAsString(updateDistillationPlanDto), id);
        return this.distillationPlanService.updatePlan(id, updateDistillationPlanDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistillationPlanDto createPlan(@RequestBody @Validated CreateDistillationPlanDto createDistillationPlanDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.POST /api/plans : {}", new ObjectMapper().writeValueAsString(createDistillationPlanDto));
        return this.distillationPlanService.createDistillationPlan(createDistillationPlanDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlan(@PathVariable("id") Long id) throws JsonProcessingException {
        LOGGER.debug("Received Delete Request /api/plans : {}", new ObjectMapper().writeValueAsString(id));
        this.distillationPlanService.deleteDistillationPlan(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationPlanDto> getAllPlans() {
        LOGGER.debug("Received Http.GET /api/getAll");
        return this.distillationPlanService.getAll();
    }
}
