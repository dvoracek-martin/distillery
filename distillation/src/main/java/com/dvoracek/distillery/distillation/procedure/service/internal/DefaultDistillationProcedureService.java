package com.dvoracek.distillery.distillation.procedure.service.internal;

import com.dvoracek.distillery.distillation.procedure.model.CreateDistillationProcedureDto;
import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedureDto;
import com.dvoracek.distillery.distillation.procedure.repository.DistillationProcedureRepository;
import com.dvoracek.distillery.distillation.procedure.service.DistillationProcedureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@Transactional
public class DefaultDistillationProcedureService implements DistillationProcedureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDistillationProcedureService.class);

    private final DistillationProcedureRepository distillationProcedureRepository;

    public DefaultDistillationProcedureService(DistillationProcedureRepository distillationProcedureRepository) {
        this.distillationProcedureRepository = distillationProcedureRepository;
    }

    @Override
    public DistillationProcedureDto getDistillationProcedure(Long id) {
        return DistillationProcedureDto.toDistillationProcedureDto((distillationProcedureRepository.findById(id)).orElseThrow(() -> new DistillationProcedureNotFoundException(id)));
    }

    @Override
    public List<DistillationProcedureDto> getAll() {
        return distillationProcedureRepository.findAll().stream().map(DistillationProcedureDto::toDistillationProcedureDto).sorted(comparing(DistillationProcedureDto::getId)).collect(Collectors.toList());
    }

    @Override
    public DistillationProcedureDto createDistillationProcedure(CreateDistillationProcedureDto createDistillationProcedureDto) {
        return null;
    }

    @Override
    public void deleteDistillationProcedure(Long id) {

    }
}
