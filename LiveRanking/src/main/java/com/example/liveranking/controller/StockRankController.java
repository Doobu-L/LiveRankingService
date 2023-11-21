package com.example.liveranking.controller;

import com.example.liveranking.domain.RankFilter;
import com.example.liveranking.service.StockRankService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
@Tag(name = "실시간 주식 랭킹조회")
public class StockRankController {

    private final StockRankService stockRankingService;

    @GetMapping("/ranks")
    @Tag(name = "실시간 주식 랭킹 조회 api" , description = "page >= 1")
    public ResponseEntity getRanks(
            @RequestParam(name = "filter") RankFilter filter,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "order", defaultValue = "DESC")SortParameters.Order order
            ) {
        return ResponseEntity.ok(stockRankingService.getStockLiveRankingList(filter, page, size,order));
    }

}
