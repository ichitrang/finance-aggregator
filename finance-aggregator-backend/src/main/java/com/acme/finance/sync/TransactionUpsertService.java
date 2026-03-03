package com.acme.finance.sync;

import com.acme.finance.domain.bank.BankAccount;
import com.acme.finance.domain.txn.Transaction;
import com.acme.finance.domain.user.User;
import com.acme.finance.repo.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionUpsertService {

    private final TransactionRepository txnRepo;

    public TransactionUpsertService(TransactionRepository txnRepo) {
        this.txnRepo = txnRepo;
    }

    /**
     * Idempotent upsert by (user, provider, provider_txn_id).
     * This prevents duplicates when you re-sync overlapping date ranges.
     */
    @Transactional
    public UpsertResult upsertFromProvider(User user, BankAccount acct, List<Map<String, Object>> providerTxns) {
        int inserted = 0, updated = 0, skipped = 0;

        for (Map<String, Object> pt : providerTxns) {
            String providerTxnId = (String) pt.get("transaction_id");
            if (providerTxnId == null) { skipped++; continue; }

            var existingOpt = txnRepo.findByUser_IdAndProviderAndProviderTxnId(user.getId(), "PLAID", providerTxnId);

            Transaction t = existingOpt.orElseGet(Transaction::new);
            boolean isNew = existingOpt.isEmpty();

            // Map fields (keep mapping in one place for maintainability)
            t.setUser(user);
            t.setBankAccount(acct);
            t.setProviderTxnId(providerTxnId);
            t.setPostedDate(LocalDate.parse((String) pt.get("date")));
            t.setAmount(new BigDecimal(String.valueOf(pt.get("amount")))); // Plaid amount numeric

            t.setMerchantName((String) pt.getOrDefault("merchant_name", null));
            t.setCategory(extractTopCategory(pt)); // "Food and Drink" etc.

            txnRepo.save(t);
            if (isNew) inserted++; else updated++;
        }

        return new UpsertResult(inserted, updated, skipped);
    }

    private String extractTopCategory(Map<String, Object> pt) {
        Object cat = pt.get("category");
        if (cat instanceof List<?> l && !l.isEmpty()) return String.valueOf(l.get(0));
        return "Uncategorized";
    }

    public record UpsertResult(int inserted, int updated, int skipped) {}
}