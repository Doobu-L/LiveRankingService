package com.example.liveranking.service;

import com.example.liveranking.domain.entity.StockInfo;
import com.example.liveranking.domain.repository.RankingRedisRepository;
import com.example.liveranking.domain.repository.StockInfoRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private RankingRedisRepository rankingRedisRepository;

    @Mock
    private StockInfoRedisRepository stockInfoRedisRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void incrementViewCount() {
        Integer stockCode = 123;

        doNothing().when(rankingRedisRepository).incrementViewCount(anyString());

        stockService.incrementViewCount(stockCode);

        verify(rankingRedisRepository, times(1)).incrementViewCount(eq(String.valueOf(stockCode)));
    }

    @Test
    void tradeStockEvent() {
        Integer stockCode = 456;
        BigDecimal tradePrice = BigDecimal.valueOf(100.50);
        Integer quantity = 10;

        when(stockInfoRedisRepository.findById(anyString())).thenReturn(Optional.of(createMockStockInfo()));
        doNothing().when(rankingRedisRepository).updateStockTradeVolume(any());
        doNothing().when(rankingRedisRepository).updateDailyChangeRate(any());

        stockService.tradeStockEvent(stockCode, tradePrice, quantity);

        verify(stockInfoRedisRepository, times(1)).findById(anyString());
        verify(rankingRedisRepository, times(1)).updateStockTradeVolume(any());
        verify(rankingRedisRepository, times(1)).updateDailyChangeRate(any());
    }

    @Test
    void updateStockTradeVolume() {
        Integer stockCode = 789;
        BigDecimal tradeVolume = BigDecimal.valueOf(500.75);

        when(stockInfoRedisRepository.findById(anyString())).thenReturn(Optional.of(createMockStockInfo()));
        when(stockInfoRedisRepository.save(any())).thenReturn(createMockStockInfo());
        doNothing().when(rankingRedisRepository).updateStockTradeVolume(any());

        stockService.updateStockTradeVolume(stockCode, tradeVolume);

        verify(stockInfoRedisRepository, times(1)).findById(anyString());
        verify(stockInfoRedisRepository, times(1)).save(any());
        verify(rankingRedisRepository, times(1)).updateStockTradeVolume(any());
    }

    @Test
    void updateDailyChangeRate() {
        Integer stockCode = 101;
        BigDecimal tradePrice = BigDecimal.valueOf(50.25);

        when(stockInfoRedisRepository.findById(anyString())).thenReturn(Optional.of(createMockStockInfo()));
        when(stockInfoRedisRepository.save(any())).thenReturn(createMockStockInfo());
        doNothing().when(rankingRedisRepository).updateDailyChangeRate(any());

        stockService.updateDailyChangeRate(stockCode, tradePrice);

        verify(stockInfoRedisRepository, times(1)).findById(anyString());
        verify(stockInfoRedisRepository, times(1)).save(any());
        verify(rankingRedisRepository, times(1)).updateDailyChangeRate(any());
    }

    private StockInfo createMockStockInfo() {
        return new StockInfo("123", "test", BigDecimal.valueOf(100.0), BigDecimal.valueOf(110.0),
                0.1, BigDecimal.valueOf(1000), 50);
    }
}
