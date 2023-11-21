package com.example.liveranking.service;

import com.example.liveranking.domain.entity.StockInfo;
import com.example.liveranking.domain.repository.RankingRedisRepository;
import com.example.liveranking.domain.repository.StockInfoRedisRepository;
import com.example.liveranking.utility.GeneratorUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class StockService {
    private final RankingRedisRepository rankingRedisRepository;
    private final StockInfoRedisRepository stockInfoRedisRepository;

    public void incrementViewCount(Integer stockCode) {
        rankingRedisRepository.incrementViewCount(String.valueOf(stockCode));
    }

    public void tradeStockEvent(Integer stockCode, BigDecimal tradePrice, Integer quantity) {
        BigDecimal addVolume = tradePrice.multiply(BigDecimal.valueOf(quantity));
        updateStockTradeVolume(stockCode,addVolume);
        updateDailyChangeRate(stockCode, tradePrice);
    }

    public void updateStockTradeVolume(Integer stockCode, BigDecimal tradeVolume) {
        StockInfo stockInfo = getStockInfo(stockCode);
        stockInfo.addVolume(tradeVolume);
        stockInfoRedisRepository.save(stockInfo);
        rankingRedisRepository.updateStockTradeVolume(stockInfo);
    }

    public void updateDailyChangeRate(Integer stockCode, BigDecimal tradePrice) {
        StockInfo stockInfo = getStockInfo(stockCode);
        stockInfo.updateCurrentPrice(tradePrice);
        stockInfoRedisRepository.save(stockInfo);
        rankingRedisRepository.updateDailyChangeRate(stockInfo);
    }

    private StockInfo getStockInfo(Integer stockCode){
        String id = GeneratorUtility.stockInfoIdGeneratorWithDate(stockCode);
        return stockInfoRedisRepository.findById(id).orElseThrow(()->new RuntimeException("stockInfo가 존재하지 않습니다."));
    }
}
