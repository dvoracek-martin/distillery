package com.dvoracek.distillery.domain.exchange.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistillationExchangeDataRepository extends JpaRepository<DistillationExchangeData, Long> {
    DistillationExchangeData findFirstByOrderByIdDesc();
}
