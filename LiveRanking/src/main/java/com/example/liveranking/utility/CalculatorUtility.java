package com.example.liveranking.utility;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class CalculatorUtility {

    public static BigDecimal calculateChangRate(BigDecimal pre, BigDecimal cur){
        return cur
                .subtract(pre)
                .divide(pre, 4, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(100));
    }
}
