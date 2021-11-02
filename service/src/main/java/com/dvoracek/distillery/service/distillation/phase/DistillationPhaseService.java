package com.dvoracek.distillery.service.distillation.phase;

import com.dvoracek.distillery.service.distillation.phase.internal.CreateDistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.internal.DistillationPhaseDto;
import com.dvoracek.distillery.service.distillation.phase.internal.UpdateDistillationPhaseDto;

import java.util.List;

public interface DistillationPhaseService {
    List<DistillationPhaseDto> getAll();

    DistillationPhaseDto updatePhase(Long id, UpdateDistillationPhaseDto updateDistillationPhaseDto);

    DistillationPhaseDto getDistillationPhase(Long id);

    DistillationPhaseDto createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto);

    void deleteDistillationPhase(Long id);

    void jumpToNextPhase();
}
