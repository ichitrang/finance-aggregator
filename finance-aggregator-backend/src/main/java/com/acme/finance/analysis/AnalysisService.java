package com.acme.finance.analysis;

import com.acme.finance.analysis.dto.*;
import com.acme.finance.repo.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class AnalysisService {

    private final TransactionRepository txnRepo;

    public AnalysisService(TransactionRepository txnRepo) {
        this.txnRepo = txnRepo;
    }

    public List<CategorySpendDto> spendingByCategory(UUID userId, int days) {
        LocalDate from = LocalDate.now().minusDays(days);
        List<Object[]> rows = txnRepo.spendingByCategory(userId, from);

        List<CategorySpendDto> out = new ArrayList<>();
        for (Object[] r : rows) {
            String cat = (String) r[0];
            BigDecimal total = (BigDecimal) r[1];
            out.add(new CategorySpendDto(cat, total));
        }
        return out;
    }

    public TrendDto trendsLast30Days(UUID userId) {
        LocalDate from = LocalDate.now().minusDays(30);
        LocalDate to = LocalDate.now();

        var txns = txnRepo.findForUserInRange(userId, from, to);

        BigDecimal spending = BigDecimal.ZERO;
        BigDecimal income = BigDecimal.ZERO;

        for (var t : txns) {
            // Convention used in schema:
            // amount > 0 => spending/outflow
            // amount < 0 => income/inflow (refunds/credits)
            if (t.getAmount().compareTo(BigDecimal.ZERO) > 0) spending = spending.add(t.getAmount());
            else income = income.add(t.getAmount().abs());
        }

        BigDecimal net = income.subtract(spending);
        BigDecimal avgDaily = spending.divide(BigDecimal.valueOf(30), 2, java.math.RoundingMode.HALF_UP);

        return new TrendDto(spending, income, net, avgDaily);
    }
}