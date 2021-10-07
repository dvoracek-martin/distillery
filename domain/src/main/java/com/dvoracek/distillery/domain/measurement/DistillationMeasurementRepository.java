package com.dvoracek.distillery.domain.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationMeasurementRepository extends JpaRepository<DistillationMeasurement, Long> {
    DistillationMeasurement findFirstByOrderByIdDesc();
}
