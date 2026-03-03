package com.acme.finance.sync;

import com.acme.finance.domain.bank.BankAccount;
import com.acme.finance.plaid.PlaidIntegrationService;
import com.acme.finance.plaid.dto.TransactionsSyncResult;
import com.acme.finance.repo.BankAccountRepository;
import com.acme.finance.repo.UserRepository;
import com.acme.finance.util.CryptoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TransactionSyncService {

    private final BankAccountRepository bankRepo;
    private final UserRepository userRepo;
    private final PlaidIntegrationService plaid;
    private final CryptoService crypto;
    private final TransactionUpsertService upsert;

    public TransactionSyncService(
            BankAccountRepository bankRepo,
            UserRepository userRepo,
            PlaidIntegrationService plaid,
            CryptoService crypto,
            TransactionUpsertService upsert
    ) {
        this.bankRepo = bankRepo;
        this.userRepo = userRepo;
        this.plaid = plaid;
        this.crypto = crypto;
        this.upsert = upsert;
    }

    /**
     * Automated Sync Strategy (simple + reliable):
     * - Pull last N days (e.g., 30) every time (overlapping window)
     * - Upsert by provider transaction id to avoid duplicates
     * - This handles late-arriving/updated transactions and pending->posted changes
     *
     * Later upgrade:
     * - Use cursor-based incremental sync if provider supports it (Plaid /transactions/sync)
     */
    public TransactionsSyncResult syncLast30Days(UUID userId, UUID bankAccountId) {
        var user = userRepo.findById(userId).orElseThrow();
        BankAccount acct = bankRepo.findById(bankAccountId).orElseThrow();

        String accessToken = crypto.decrypt(acct.getPlaidAccessTokenEnc());

        LocalDate to = LocalDate.now(ZoneOffset.UTC);
        LocalDate from = to.minusDays(30);

        var providerTxns = plaid.fetchTransactions(accessToken, from, to);
        var res = upsert.upsertFromProvider(user, acct, providerTxns);

        acct.setLastSyncedAt(java.time.Instant.now());
        bankRepo.save(acct);

        return new TransactionsSyncResult(res.inserted(), res.updated(), res.skipped());
    }
}