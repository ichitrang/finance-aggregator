package com.acme.finance.repo;

import com.acme.finance.domain.txn.Transaction;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.*;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  Optional<Transaction> findByUser_IdAndProviderAndProviderTxnId(
          UUID userId,
          String provider,
          String providerTxnId
  );

  @Query("""
        select t from Transaction t
        where t.user.id = :userId
        and t.postedDate between :from and :to
        order by t.postedDate desc
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
        order by t.postedDate desc
    """)
  Page<Transaction> findByUserAndCategoryAndDateRange(
          @Param("userId") UUID userId,
          @Param("category") String category,
          @Param("from") LocalDate from,
          @Param("to") LocalDate to,
          Pageable pageable
  );

  @Query("""
        select t
        from Transaction t
        where t.user.id = :userId
          and t.postedDate between :from and :to
        order by t.postedDate desc
    """)
  List<Transaction> findForUserInRange(
          @Param("userId") UUID userId,
          @Param("from") LocalDate from,
          @Param("to") LocalDate to
  );

  @Query("""
        select coalesce(t.category, 'Uncategorized'), sum(t.amount)
        from Transaction t
        where t.user.id = :userId
          and t.postedDate >= :from
          and t.amount > 0
        group by coalesce(t.category, 'Uncategorized')
        order by sum(t.amount) desc
    """)
  List<Object[]> spendingByCategory(
          @Param("userId") UUID userId,
          @Param("from") LocalDate from
  );
}