package com.dvoracek.distillery.web.distillation.exchange.data;

import com.dvoracek.distillery.service.distillation.exchange.data.DistillationExchangeDataService;
import com.dvoracek.distillery.service.distillation.exchange.data.internal.CreateDistillationExchangeDataDto;
import com.dvoracek.distillery.service.distillation.exchange.data.internal.DistillationExchangeDataDto;
import com.dvoracek.distillery.web.distillation.phase.DistillationPhaseController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/data")
@CrossOrigin(origins = "http://localhost:4200")
public class DistillationExchangeDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistillationPhaseController.class);

    private final DistillationExchangeDataService distillationExchangeDataService;

    public DistillationExchangeDataController(DistillationExchangeDataService distillationExchangeDataService) {
        this.distillationExchangeDataService = distillationExchangeDataService;
    }

    @GetMapping("/last")
    @ResponseStatus(HttpStatus.OK)
    public DistillationExchangeDataDto getLastExchangeData() {
        LOGGER.debug("Received Http.GET /api/data/last");
        return this.distillationExchangeDataService.findFirstByOrderByIdDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistillationExchangeDataDto createExchangeData(@RequestBody @Validated CreateDistillationExchangeDataDto createDistillationExchangeDataDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.POST /api/data : {}", new ObjectMapper().writeValueAsString(createDistillationExchangeDataDto));
        return this.distillationExchangeDataService.createDistillationExchangeData(createDistillationExchangeDataDto);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllExchangeData() {
        LOGGER.debug("Received Delete Request /api/data");
        this.distillationExchangeDataService.deleteAll();
    }
}
