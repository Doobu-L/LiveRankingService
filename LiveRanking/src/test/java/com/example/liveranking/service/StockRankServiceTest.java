package com.example.liveranking.service;

import com.example.liveranking.domain.RankFilter;
import com.example.liveranking.domain.record.StockCard;
import com.example.liveranking.domain.repository.RankingRedisRepository;
import com.example.liveranking.domain.repository.StockInfoRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.connection.SortParameters;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

class StockRankServiceTest {

    @InjectMocks
    private StockRankService stockRankService;

    @Mock
    private RankingRedisRepository rankingRedisRepository;

    @Mock
    private StockInfoRedisRepository stockInfoRedisRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStockLiveRankingList() {
        RankFilter rankFilter = RankFilter.POPULARITY;
        int page = 1;
        int size = 10;
        SortParameters.Order order = SortParameters.Order.DESC;

        when(rankingRedisRepository.getStockRankingList(rankFilter, page, size, order))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        when(stockInfoRedisRepository.findAllById(anyList())).thenReturn(Collections.emptyList());

        Page<StockCard> result = stockRankService.getStockLiveRankingList(rankFilter, page, size, order);

        verify(rankingRedisRepository, times(1)).getStockRankingList(rankFilter, page, size, order);
        verify(stockInfoRedisRepository, times(1)).findAllById(anyList());

        assertNotNull(result);
    }
}
