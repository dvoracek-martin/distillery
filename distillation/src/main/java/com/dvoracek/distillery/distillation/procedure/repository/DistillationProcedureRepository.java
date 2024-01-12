package com.dvoracek.distillery.distillation.procedure.repository;

import com.dvoracek.distillery.distillation.procedure.model.DistillationProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationProcedureRepository extends JpaRepository<DistillationProcedure, Long> {
    @Query("SELECT distillationProcedure FROM DistillationProcedure distillationProcedure WHERE distillationProcedure.planId = :planId AND distillationProcedure.attemptNumber = :attemptNumber")
    DistillationProcedure findByPlanIdAndAttemptNumber(Long planId, int attemptNumber);

    @Query(value = "SELECT distillationProcedure FROM DistillationProcedure distillationProcedure WHERE distillationProcedure.planId = :planId AND distillationProcedure.id = (SELECT MAX(id) FROM distillationProcedure", nativeQuery = true)
    DistillationProcedure findLastByPlan(Long planId);

    @Query("SELECT distillationProcedure FROM DistillationProcedure distillationProcedure WHERE distillationProcedure.planId = :planId")
    DistillationProcedure findByPlanId(Long planId);
}
