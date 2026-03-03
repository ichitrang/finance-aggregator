// analysis/dto/TrendDto.java
package com.acme.finance.analysis.dto;

import java.math.BigDecimal;

public record TrendDto(
        BigDecimal totalSpending,
        BigDecimal totalIncome,
        BigDecimal net,                 // income - spending
        BigDecimal avgDailySpending
) {}