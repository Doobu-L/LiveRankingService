package com.example.liveranking.domain.record;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record StockCardRecord(Integer stockCode, String stockName, double changeRate , BigDecimal tradeVolume, Integer viewCount) {
    //Response 용

}