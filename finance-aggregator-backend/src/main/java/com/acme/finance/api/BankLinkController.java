package com.acme.finance.api;

import com.acme.finance.plaid.PlaidIntegrationService;
import com.acme.finance.plaid.dto.*;
import com.acme.finance.domain.bank.BankAccount;
import com.acme.finance.repo.*;
import com.acme.finance.util.CryptoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/banks")
public class BankLinkController {

    private final PlaidIntegrationService plaid;
    private final UserRepository userRepo;
    private final BankAccountRepository bankRepo;
    private final CryptoService crypto;

    public BankLinkController(PlaidIntegrationService plaid, UserRepository userRepo, BankAccountRepository bankRepo, CryptoService crypto) {
        this.plaid = plaid;
        this.userRepo = userRepo;
        this.bankRepo = bankRepo;
        this.crypto = crypto;
    }

    /**
     * Called after Plaid Link returns public_token.
     * Backend exchanges it for access_token and stores it encrypted.
     */
    @PostMapping("/link")
    public LinkAccountResponse link(@Valid @RequestBody PublicTokenExchangeRequest req, Authentication auth) {
        String email = (String) auth.getPrincipal();
        var user = userRepo.findByEmail(email).orElseThrow();

        var ex = plaid.exchangePublicToken(req.publicToken());
        String encAccessToken = crypto.encrypt(ex.accessToken());

        BankAccount acct = new BankAccount();
        acct.setUser(user);
        acct.setPlaidItemId(ex.itemId());
        acct.setPlaidAccessTokenEnc(encAccessToken);

        // In real implementation, you'd fetch accounts via /accounts/get and store each account
        acct.setPlaidAccountId(req.plaidAccountId() != null ? req.plaidAccountId() : ex.itemId() + ":default");

        bankRepo.save(acct);

        return new LinkAccountResponse(acct.getId(), "LINKED");
    }
}