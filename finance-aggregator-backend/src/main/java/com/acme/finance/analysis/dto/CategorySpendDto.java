// analysis/dto/CategorySpendDto.java
package com.acme.finance.analysis.dto;

import java.math.BigDecimal;

public record CategorySpendDto(String category, BigDecimal total) {}