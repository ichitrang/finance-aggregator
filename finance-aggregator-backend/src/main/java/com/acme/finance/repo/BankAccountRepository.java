package com.acme.finance.repo;

import com.acme.finance.domain.bank.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    List<BankAccount> findByUser_Id(UUID userId);
}