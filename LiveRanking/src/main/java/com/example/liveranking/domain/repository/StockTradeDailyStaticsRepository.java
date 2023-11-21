package com.example.liveranking.domain.repository;

import com.example.liveranking.domain.entity.StockTradeDailyStatics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTradeDailyStaticsRepository extends JpaRepository<StockTradeDailyStatics,Long> {
}
