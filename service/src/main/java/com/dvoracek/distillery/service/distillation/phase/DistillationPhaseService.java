package com.dvoracek.distillery.service.distillation.phase;

import java.util.List;

public interface DistillationPhaseService {
    List<DistillationPhaseDto> getAll();

    DistillationPhaseDto updatePhase(Long id, UpdateDistillationPhaseDto updateDistillationPhaseDto);

    DistillationPhaseDto getDistillationPhase(Long id);

    DistillationPhaseDto createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto);

    void deleteDistillationPhase(Long id);
}
