package com.example.liveranking.controller;

import com.example.liveranking.service.StockRankService;
import com.example.liveranking.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Deprecated
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Tag(name = "테스트 컨트롤러")
public class TestInputController {
    private final StockService stockService;

    @PutMapping("/{stockCode}")
    public ResponseEntity updateViewCount(@PathVariable("stockCode") Integer stockCode) {
        stockService.incrementViewCount(stockCode);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }

    @PutMapping("/{stockCode}/trades")
    public ResponseEntity completedStockTransaction(
            @PathVariable("stockCode") Integer stockCode,
            @RequestParam("tradePrice") BigDecimal tradePrice,
            @RequestParam("quantity") Integer quantity) {
        stockService.tradeStockEvent(stockCode, tradePrice, quantity);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).build();
    }
}
