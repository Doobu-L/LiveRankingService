package com.example.liveranking.service;

import com.example.liveranking.domain.RankFilter;
import com.example.liveranking.domain.entity.StockInfo;
import com.example.liveranking.domain.record.StockCard;
import com.example.liveranking.domain.repository.RankingRedisRepository;
import com.example.liveranking.domain.repository.StockInfoRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockRankService {

    private final RankingRedisRepository rankingRedisRepository;
    private final StockInfoRedisRepository stockInfoRedisRepository;

    public Page<StockCard> getStockLiveRankingList(RankFilter rankFilter, int page, int size , SortParameters.Order order) {
        Page<ZSetOperations.TypedTuple<String>> stockRankingList = rankingRedisRepository.getStockRankingList(rankFilter, page, size , order);
        List<String> ids = stockRankingList.stream().map(it-> it.getValue() +"_"+ LocalDate.now()).toList();
        Map<String,StockInfo> stockInfos = stockInfoRedisRepository.findAllById(ids).stream().collect(Collectors.toMap(StockInfo::getStockCode, Function.identity()));
        return stockRankingList.map(tuple-> convertStockInfoToStockCard(stockInfos.get(String.valueOf(tuple.getValue()))));
    }

    private StockCard convertStockInfoToStockCard(StockInfo it){
        return  new StockCard(
                    Integer.parseInt(it.getStockCode()),
                    it.getStockName(),
                    it.getChangeRate(),
                    it.getTradeVolume(),
                    it.getViewCount());
    }
}
