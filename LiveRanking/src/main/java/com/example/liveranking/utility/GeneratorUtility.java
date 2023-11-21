package com.example.liveranking.utility;

import com.example.liveranking.domain.RankFilter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class GeneratorUtility {

    public static String stockInfoIdGeneratorWithDate(Integer stockCode){
        return String.join("_",String.valueOf(stockCode), String.valueOf(LocalDate.now()));
    }

    public static String stockInfoIdGeneratorWithDate(String stockCode){
        return String.join("_",stockCode, String.valueOf(LocalDate.now()));
    }

    public static String rankFilterKeyGenerateWithDate(RankFilter rankFilter){
            return String.join("_",rankFilter.name(),String.valueOf(LocalDate.now()));
    }


}
