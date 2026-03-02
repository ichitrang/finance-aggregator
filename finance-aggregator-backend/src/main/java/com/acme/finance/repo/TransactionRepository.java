package com.acme.finance.repo;

import com.acme.finance.domain.txn.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.*;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  @Query("""
    select t from Transaction t
    where t.user.id = :userId
      and t.postedDate between :from and :to
    order by t.postedDate desc
  """)
  List<Transaction> findForUserInRange(@Param("userId") UUID userId,
                                      @Param("from") LocalDate from,
                                      @Param("to") LocalDate to);

  Optional<Transaction> findByUser_IdAndProviderAndProviderTxnId(UUID userId, String provider, String providerTxnId);

  @Query("""
    select coalesce(t.category, 'Uncategorized') as cat, sum(t.amount) as total
    from Transaction t
    where t.user.id = :userId
      and t.postedDate >= :from
      and t.amount > 0
    group by coalesce(t.category, 'Uncategorized')
    order by total desc
  """)
  List<Object[]> spendingByCategory(@Param("userId") UUID userId, @Param("from") LocalDate from);
}