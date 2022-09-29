package com.dvoracek.distillery.distillation.plan.repository;

import com.dvoracek.distillery.distillation.plan.model.DistillationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationPlanRepository extends JpaRepository<DistillationPlan, Long> {
}
