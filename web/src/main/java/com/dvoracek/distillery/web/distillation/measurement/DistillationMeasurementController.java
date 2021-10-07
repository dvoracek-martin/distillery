package com.dvoracek.distillery.web.distillation.measurement;

import com.dvoracek.distillery.service.distillation.measurement.internal.CreateDistillationMeasurementDto;
import com.dvoracek.distillery.service.distillation.measurement.internal.DistillationMeasurementDto;
import com.dvoracek.distillery.service.distillation.measurement.DistillationMeasurementService;
import com.dvoracek.distillery.web.distillation.phase.DistillationPhaseController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/measurement")
@CrossOrigin(origins = "http://localhost:4200")
public class DistillationMeasurementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistillationPhaseController.class);

    private final DistillationMeasurementService distillationMeasurementService;

    public DistillationMeasurementController(DistillationMeasurementService distillationMeasurementService) {
        this.distillationMeasurementService = distillationMeasurementService;
    }



    @GetMapping("/last")
    @ResponseStatus(HttpStatus.OK)
    public DistillationMeasurementDto getLastMeasurement() {
        LOGGER.debug("Received Http.GET /api/measurement/last");
        return this.distillationMeasurementService.findFirstByOrderByIdDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistillationMeasurementDto createMeasurement(@RequestBody @Validated CreateDistillationMeasurementDto createDistillationMeasurementDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.POST /api/measurement : {}", new ObjectMapper().writeValueAsString(createDistillationMeasurementDto));
        return this.distillationMeasurementService.createDistillationMeasurement(createDistillationMeasurementDto);
    }
}
