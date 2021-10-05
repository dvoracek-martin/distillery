package com.dvoracek.distillery.web.distillation.phase;

import com.dvoracek.distillery.service.distillation.phase.CreateDistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.DistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.DistillationPhaseService;
import com.dvoracek.distillery.service.distillation.phase.UpdateDistillationPhaseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/phases")
@CrossOrigin(origins = "*")
public class PhaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhaseController.class);

    private final DistillationPhaseService distillationPhaseService;

    public PhaseController(DistillationPhaseService distillationPhaseService) {
        this.distillationPhaseService = distillationPhaseService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPhaseDto getPhase(@PathVariable("id") Long id) {
        LOGGER.debug("Received Http.GET /api/phases/" + id);
        return this.distillationPhaseService.getDistillationPhase(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DistillationPhaseDto updatePhase(@PathVariable("id") Long id, @RequestBody @Validated UpdateDistillationPhaseDto updateDistillationPhaseDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.PUT /api/phases : {} with an id: {}", new ObjectMapper().writeValueAsString(updateDistillationPhaseDto), id);
        return this.distillationPhaseService.updatePhase(id, updateDistillationPhaseDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistillationPhaseDto createPhase(@RequestBody @Validated CreateDistillationPhaseDto createDistillationPhaseDto) throws JsonProcessingException {
        LOGGER.debug("Received Http.POST /api/phases : {}", new ObjectMapper().writeValueAsString(createDistillationPhaseDto));
        return this.distillationPhaseService.createDistillationPhase(createDistillationPhaseDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePhase(@PathVariable("id") Long id) throws JsonProcessingException {
        LOGGER.debug("Received Delete Request /api/phases : {}", new ObjectMapper().writeValueAsString(id));
        this.distillationPhaseService.deleteDistillationPhase(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<DistillationPhaseDto> getAllPhases() {
        LOGGER.debug("Received Http.GET /api/getAll");
        return this.distillationPhaseService.getAll();
    }
}
