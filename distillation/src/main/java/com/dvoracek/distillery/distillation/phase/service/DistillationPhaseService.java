package com.dvoracek.distillery.distillation.phase.service;


import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.service.internal.CreateDistillationPhaseDto;

public interface DistillationPhaseService {
    DistillationPhase createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto);
    DistillationPhase findById(Long id);
}
