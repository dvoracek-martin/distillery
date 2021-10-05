package com.dvoracek.distillery.domain.plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationPlanRepository extends JpaRepository<DistillationPlan, Long> {
}
