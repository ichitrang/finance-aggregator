package com.acme.finance.api;

import com.acme.finance.domain.txn.Transaction;
import com.acme.finance.repo.TransactionRepository;
import com.acme.finance.repo.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionController(TransactionRepository transactionRepository,
                                 UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Fetch paginated transactions for authenticated user.
     *
     * Example:
     * GET /api/transactions?page=0&size=20&from=2024-01-01&to=2024-02-01&category=Food
     */
    @GetMapping
    public Page<Transaction> getTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {

        String email = (String) authentication.getPrincipal();
        UUID userId = userRepository.findByEmail(email)
                .orElseThrow()
                .getId();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "postedDate")
        );

        LocalDate fromDate = from != null ? LocalDate.parse(from) : LocalDate.now().minusYears(1);
        LocalDate toDate = to != null ? LocalDate.parse(to) : LocalDate.now();

        if (category != null && !category.isBlank()) {
            return transactionRepository.findByUserAndCategoryAndDateRange(
                    userId,
                    category,
                    fromDate,
                    toDate,
                    pageable
            );
        }

        return transactionRepository.findByUserAndDateRange(
                userId,
                fromDate,
                toDate,
                pageable
        );
    }
}