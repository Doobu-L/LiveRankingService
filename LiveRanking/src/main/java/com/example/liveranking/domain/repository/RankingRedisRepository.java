package com.example.liveranking.domain.repository;

import com.example.liveranking.domain.RankFilter;
import com.example.liveranking.domain.entity.StockInfo;
import com.example.liveranking.utility.GeneratorUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RankingRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ZSetOperations<String,String> zSetOperations;
    private final StockInfoRedisRepository stockInfoRedisRepository;


    public RankingRedisRepository(RedisTemplate<String,String> redisTemplate, StockInfoRedisRepository stockInfoRedisRepository){
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
        this.stockInfoRedisRepository = stockInfoRedisRepository;
    }

    public void initRankKeyWithTTL(){
        for(RankFilter rankFilter : RankFilter.values()){
            String key = GeneratorUtility.rankFilterKeyGenerateWithDate(rankFilter);
            zSetOperations.add(key,"100",0);
            redisTemplate.expire(key, 24, TimeUnit.HOURS);
        }
    }

    public void incrementViewCount(String stockCode){
        String key = GeneratorUtility.rankFilterKeyGenerateWithDate(RankFilter.POPULARITY);
        Double resultViewCount = zSetOperations.incrementScore(key, stockCode , 1);

        String id = GeneratorUtility.stockInfoIdGeneratorWithDate(stockCode);
        StockInfo stockInfo = stockInfoRedisRepository.findById(id).orElseThrow();
        stockInfo.updateViewCount(resultViewCount.intValue());
        stockInfoRedisRepository.save(stockInfo);
    }

    public void updateStockTradeVolume(StockInfo stockInfo){
        String key = GeneratorUtility.rankFilterKeyGenerateWithDate(RankFilter.TRADE_VOLUME);
        zSetOperations.add(key,stockInfo.getStockCode(),stockInfo.getTradeVolume().doubleValue());
    }

    public void updateDailyChangeRate(StockInfo stockInfo){
        String key = GeneratorUtility.rankFilterKeyGenerateWithDate(RankFilter.CHANGE_RATE);
        zSetOperations.add(key,stockInfo.getStockCode(),stockInfo.getChangeRate());
    }

    public Page<ZSetOperations.TypedTuple<String>> getStockRankingList(RankFilter rankFilter, int page, int size , SortParameters.Order order){
        int startIndex = (page - 1) * size;
        int endIndex = startIndex + size - 1;

        String key = GeneratorUtility.rankFilterKeyGenerateWithDate(rankFilter);
        Set<ZSetOperations.TypedTuple<String>> result;
        if(SortParameters.Order.DESC.equals(order))
            result = zSetOperations.reverseRangeWithScores(key,startIndex,endIndex);
        else
            result = zSetOperations.rangeWithScores(key,startIndex,endIndex);

        long totalElements = zSetOperations.size(key);
        return new PageImpl<>(new ArrayList<>(result),PageRequest.of(page,size),totalElements);
    }
}
