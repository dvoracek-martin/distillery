package com.dvoracek.distillery.distillation.phase.repository;

import com.dvoracek.distillery.distillation.phase.model.DistillationPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationPhaseRepository extends JpaRepository<DistillationPhase, Long> {
}
