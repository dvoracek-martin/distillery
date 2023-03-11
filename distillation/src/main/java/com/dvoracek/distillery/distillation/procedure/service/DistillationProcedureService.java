package com.dvoracek.distillery.distillation.procedure.service;

import com.dvoracek.distillery.distillation.procedure.model.CreateDistillationProcedureDto;
import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedureDto;

import java.util.List;

public interface DistillationProcedureService {

    DistillationProcedureDto getDistillationProcedure(Long id);

    List<DistillationProcedureDto> getAll();

    DistillationProcedureDto createDistillationProcedure(CreateDistillationProcedureDto createDistillationProcedureDto);

    void deleteDistillationProcedure(Long id);
}
