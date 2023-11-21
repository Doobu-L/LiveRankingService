package com.example.liveranking.config;

import com.example.liveranking.domain.entity.StockInfo;
import com.example.liveranking.domain.entity.StockTradeDailyStatics;
import com.example.liveranking.domain.repository.StockInfoRedisRepository;
import com.example.liveranking.domain.repository.StockTradeDailyStaticsRepository;
import com.example.liveranking.service.StockService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class TestDataGenerator {

    private final StockTradeDailyStaticsRepository stockTradeDailyStaticsRepository;
    private final StockInfoRedisRepository stockInfoRedisRepository;
    private final StockService stockService;


    @PostConstruct
    public void settingSampleDataToRedis() {
        //테스트 데이터 세팅
        List<StockTradeDailyStatics> stockTradeDailyStaticsList = stockTradeDailyStaticsRepository.findAll();

        stockTradeDailyStaticsList.stream()
                .forEach(it -> {
                    stockInfoRedisRepository.save(new StockInfo(it));
                    stockService.updateDailyChangeRate(it.getStockCode(), it.getCurrentPrice());
                    stockService.updateStockTradeVolume(it.getStockCode(), BigDecimal.ZERO);
                    stockService.incrementViewCount(it.getStockCode());
                });

        samplRequestLetsGo();
    }

    private void samplRequestLetsGo() {
        for (int i = 0; i < 100; i++) {
            stockService.incrementViewCount(5930);
            stockService.incrementViewCount(373220);
            stockService.incrementViewCount(660);
            stockService.incrementViewCount(207940);
            stockService.incrementViewCount(51910);
            stockService.incrementViewCount(5380);
            stockService.incrementViewCount(270);
            stockService.incrementViewCount(6360);
            stockService.incrementViewCount(6360);
            stockService.incrementViewCount(6360);
            stockService.incrementViewCount(6360);
            stockService.incrementViewCount(7070);
            stockService.incrementViewCount(8770);
            stockService.incrementViewCount(377300);
            stockService.incrementViewCount(377300);
            stockService.incrementViewCount(377300);
            stockService.incrementViewCount(35720);
            stockService.incrementViewCount(35720);

        }

        stockService.updateDailyChangeRate(5930, BigDecimal.valueOf(63000));
        stockService.updateDailyChangeRate(373220, BigDecimal.valueOf(455000));
        stockService.updateDailyChangeRate(660, BigDecimal.valueOf(95700));
        stockService.updateDailyChangeRate(207940, BigDecimal.valueOf(1000000));
        stockService.updateDailyChangeRate(5380, BigDecimal.valueOf(210000));
        stockService.updateDailyChangeRate(35720, BigDecimal.valueOf(150000));
        stockService.updateDailyChangeRate(377300, BigDecimal.valueOf(150000));
        stockService.updateDailyChangeRate(6360, BigDecimal.valueOf(3150000));
        stockService.updateDailyChangeRate(7070, BigDecimal.valueOf(20000));
        stockService.updateDailyChangeRate(8770, BigDecimal.valueOf(70000));
        stockService.updateDailyChangeRate(352820, BigDecimal.valueOf(100000));
        stockService.updateDailyChangeRate(6800, BigDecimal.valueOf(3000));
        stockService.updateDailyChangeRate(5940, BigDecimal.valueOf(8000));
        stockService.updateDailyChangeRate(8560, BigDecimal.valueOf(2000));
        stockService.updateDailyChangeRate(16360, BigDecimal.valueOf(34000));

        stockService.updateStockTradeVolume(5930, BigDecimal.valueOf(123213500));
        stockService.updateStockTradeVolume(373220, BigDecimal.valueOf(24024000));
        stockService.updateStockTradeVolume(660, BigDecimal.valueOf(34343000));
        stockService.updateStockTradeVolume(207940, BigDecimal.valueOf(234200));
        stockService.updateStockTradeVolume(5380, BigDecimal.valueOf(566000));
        stockService.updateStockTradeVolume(35720, BigDecimal.valueOf(5646000));
        stockService.updateStockTradeVolume(377300, BigDecimal.valueOf(34534500));
        stockService.updateStockTradeVolume(6360, new BigDecimal("99999999999"));
        stockService.updateStockTradeVolume(7070, BigDecimal.valueOf(44554300));
        stockService.updateStockTradeVolume(8770, BigDecimal.valueOf(3450000));
        stockService.updateStockTradeVolume(352820, BigDecimal.valueOf(435600));
        stockService.updateStockTradeVolume(6800, BigDecimal.valueOf(80000));
        stockService.updateStockTradeVolume(5940, BigDecimal.valueOf(45457));
        stockService.updateStockTradeVolume(8560, BigDecimal.valueOf(756756));
        stockService.updateStockTradeVolume(16360, BigDecimal.valueOf(56756756));

    }
}
