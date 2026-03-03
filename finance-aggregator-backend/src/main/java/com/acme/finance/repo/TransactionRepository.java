package com.acme.finance.repo;

import com.acme.finance.domain.txn.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.*;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  @Query("""
    select t from Transaction t
    where t.user.id = :userId
      and t.postedDate between :from and :to
""")
  Page<Transaction> findByUserAndDateRange(
          @Param("userId") UUID userId,
          @Param("from") LocalDate from,
          @Param("to") LocalDate to,
          Pageable pageable
  );

  @Query("""
    select t from Transaction t
    where t.user.id = :userId
      and t.category = :category
      and t.postedDate between :from and :to
""")
  Page<Transaction> findByUserAndCategoryAndDateRange(
          @Param("userId") UUID userId,
          @Param("category") String category,
          @Param("from") LocalDate from,
          @Param("to") LocalDate to,
          Pageable pageable
  );
}