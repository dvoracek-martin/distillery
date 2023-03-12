package com.dvoracek.distillery.distillation.procedure.controller;

import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedureDto;
import com.dvoracek.distillery.distillation.procedure.service.DistillationProcedureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/procedure")
@CrossOrigin(origins = "http://localhost:4200")
public class DistillationProcedureController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistillationProcedureController.class);

    private final DistillationProcedureService distillationProcedureService;

    public DistillationProcedureController(DistillationProcedureService distillationProcedureService) {
        this.distillationProcedureService = distillationProcedureService;
    }

    @GetMapping("/{planId}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationProcedureDto getPlan(@PathVariable("planId") Long planId) {
        LOGGER.debug("Received Http.GET /api/procedure/" + planId);
        return DistillationProcedureDto.toDistillationProcedureDto(this.distillationProcedureService.getDistillationProcedure(planId));
    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public DistillationPlanDto editPlan(@PathVariable("id") Long id, @RequestBody @Validated EditDistillationPlanDto editDistillationPlanDto) throws JsonProcessingException {
//        LOGGER.debug("Received Http.PUT /api/plan : {} with an id: {}", new ObjectMapper().writeValueAsString(editDistillationPlanDto), id);
//        return this.distillationPlanService.editPlan(id, editDistillationPlanDto);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public DistillationPlanDto createPlan(@RequestBody @Validated CreateDistillationPlanDto createDistillationPlanDto) throws JsonProcessingException {
//        LOGGER.debug("Received Http.POST /api/plan : {}", new ObjectMapper().writeValueAsString(createDistillationPlanDto));
//        return this.distillationPlanService.createDistillationPlan(createDistillationPlanDto);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void deletePlan(@PathVariable("id") Long id) throws JsonProcessingException {
//        LOGGER.debug("Received Delete Request /api/plan : {}", new ObjectMapper().writeValueAsString(id));
//        this.distillationPlanService.deleteDistillationPlan(id);
//    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationProcedureDto> getAllProcedures() {
        LOGGER.debug("Received Http.GET /api/procedure/getAll");
        return this.distillationProcedureService.getAll().stream().map(DistillationProcedureDto::toDistillationProcedureDto).collect(Collectors.toList());
    }

}
