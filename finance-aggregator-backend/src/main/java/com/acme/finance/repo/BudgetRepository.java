package com.acme.finance.repo;

import com.acme.finance.domain.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    List<Budget> findByUser_Id(UUID userId);

    Optional<Budget> findByUser_IdAndCategoryAndStartMonth(
            UUID userId,
            String category,
            LocalDate startMonth
    );

    List<Budget> findByUser_IdAndStartMonth(UUID userId, LocalDate startMonth);
}