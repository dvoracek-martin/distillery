package com.dvoracek.distillery.distillation.procedure.repository;

import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationProcedureRepository extends JpaRepository<DistillationProcedure, Long> {
}
