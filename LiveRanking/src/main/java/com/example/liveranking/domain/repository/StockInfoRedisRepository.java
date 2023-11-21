package com.example.liveranking.domain.repository;

import com.example.liveranking.domain.entity.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRedisRepository extends JpaRepository<StockInfo, String> {
}
