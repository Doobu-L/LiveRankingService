package com.example.liveranking.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_trade_daily_statics",indexes = @Index(name = "trade_date_idx",columnList = "tradeDate"))
@DynamicUpdate
@Getter
public class StockTradeDailyStatics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime tradeDate; //거래일

    @Column(nullable = false)
    private Integer stockCode; //종목코드

    @Column(nullable = false)
    private String stockName; //종목명

    @Column(columnDefinition="Decimal(14,0) default '0'")
    private BigDecimal preDayClosingPrice; //전일종가

    @Column(columnDefinition="Decimal(14,0) default '0'")
    private BigDecimal currentPrice; //당일종가

    @Column(columnDefinition="Decimal(14,0) default '0'")
    private BigDecimal tradeVolume; //거래량

    @ColumnDefault(value = "0")
    private Integer viewCount; //조회수

    @CreatedBy
    private LocalDateTime createdAt;

    @LastModifiedBy
    private LocalDateTime updatedAt;
}
