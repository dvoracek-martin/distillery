package com.dvoracek.distillery.distillation.procedure.controller;

import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedureDto;
import com.dvoracek.distillery.distillation.procedure.service.DistillationProcedureService;
import com.dvoracek.distillery.distillation.process.service.internal.DistillationProcessDataFromRaspiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/procedure")
@CrossOrigin(origins = "http://localhost:4200")
public class DistillationProcedureController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistillationProcedureController.class);

    private final DistillationProcedureService distillationProcedureService;

    public DistillationProcedureController(DistillationProcedureService distillationProcedureService) {
        this.distillationProcedureService = distillationProcedureService;
    }

    @GetMapping("/{procedureId}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationProcedureDto getProcedure(@PathVariable("procedureId") Long procedureId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Received Http.GET /api/procedure/%d", procedureId));
        }
        return DistillationProcedureDto.toDistillationProcedureDto(this.distillationProcedureService.getDistillationProcedure(procedureId));
    }

    @GetMapping("/es/{procedureId}")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationProcessDataFromRaspiDto> getProcedureDetailsFromES(@PathVariable("procedureId") Long procedureId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Received Http.GET /api/procedure/es/%d", procedureId));
        }
        return this.distillationProcedureService.getDistillationProcedureFromES(procedureId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProcedure(@PathVariable("id") Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Received Delete Request /api/procedure : %d", id));
        }
        this.distillationProcedureService.deleteDistillationProcedure(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationProcedureDto> getAllProcedures() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Received Http.GET /api/procedure/getAll");
        }
        return this.distillationProcedureService.getAll().stream().map(DistillationProcedureDto::toDistillationProcedureDto).toList();
    }

}
