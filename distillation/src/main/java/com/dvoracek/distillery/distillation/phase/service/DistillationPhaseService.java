package com.dvoracek.distillery.distillation.phase.service;


import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import com.dvoracek.distillery.distillation.phase.service.internal.CreateDistillationPhaseDto;
import com.dvoracek.distillery.distillation.phase.service.internal.DistillationPhaseDto;
import com.dvoracek.distillery.distillation.phase.service.internal.EditDistillationPhaseDto;

import java.util.List;

public interface DistillationPhaseService {
    DistillationPhase createDistillationPhase(CreateDistillationPhaseDto createDistillationPhaseDto);

    DistillationPhase findById(Long id);

    void delete(Long id);
}
