package com.dvoracek.distillery.domain.exchange.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistillationExchangeDataRepository extends JpaRepository<DistillationExchangeData, Long> {
    @Query("SELECT count(*) FROM DistillationExchangeData")
    long countExchangeData();

    //    TODO Get only last entry
    @Query("select t from DistillationExchangeData t order by t.timestamp desc ")
    List<DistillationExchangeData> findFirstByOrderByCreatedTsByDesc();
}
