package com.example.liveranking.domain.entity;

import com.example.liveranking.utility.CalculatorUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@RedisHash(value = "StockInfo" , timeToLive = 86400)
@Getter
public class StockInfo {
    @Id
    private String id;
    private String stockCode;
    private String stockName;
    private BigDecimal preClosingPrice;
    private BigDecimal currentPrice;
    private Double changeRate;
    private BigDecimal tradeVolume;
    private Integer viewCount;
    private LocalDate date;

    public StockInfo(String stockCode, String stockName, BigDecimal preClosingPrice, BigDecimal currentPrice, Double changeRate, BigDecimal tradeVolume, Integer viewCount) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.preClosingPrice = preClosingPrice;
        this.currentPrice = currentPrice;
        this.changeRate = changeRate;
        this.tradeVolume = tradeVolume;
        this.viewCount = viewCount;
        this.date = LocalDate.now();
        this.id = String.join("_",stockCode,String.valueOf(date));
    }

    public StockInfo(StockTradeDailyStatics stockTradeDailyStatics) {
        this.stockCode = String.valueOf(stockTradeDailyStatics.getStockCode());
        this.stockName = stockTradeDailyStatics.getStockName();
        this.preClosingPrice = stockTradeDailyStatics.getPreDayClosingPrice();
        this.currentPrice = stockTradeDailyStatics.getCurrentPrice();
        this.changeRate = 0.0;
        this.tradeVolume = stockTradeDailyStatics.getTradeVolume();
        this.viewCount = 0;
        this.date = LocalDate.now();
        this.id = String.join("_",stockCode,String.valueOf(date));
    }

    public void updateViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public void updateCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
        this.changeRate = CalculatorUtility.calculateChangRate(this.preClosingPrice, this.currentPrice).doubleValue();
    }

    public void addVolume(BigDecimal volume) {
        this.tradeVolume = this.tradeVolume.add(volume);
    }
}