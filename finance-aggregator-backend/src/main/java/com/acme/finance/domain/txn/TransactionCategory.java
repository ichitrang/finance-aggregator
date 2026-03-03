package com.acme.finance.domain.txn;

/**
 * High-level normalized transaction categories.
 * Used internally for analytics normalization.
 *
 * Note:
 * Raw provider category is still stored as String in DB.
 */
public enum TransactionCategory {

    FOOD,
    TRAVEL,
    SHOPPING,
    ENTERTAINMENT,
    UTILITIES,
    HEALTH,
    EDUCATION,
    TRANSFER,
    INCOME,
    INVESTMENT,
    TAX,
    RENT,
    GROCERIES,
    TRANSPORT,
    INSURANCE,
    SUBSCRIPTION,
    LOAN,
    OTHER;

    /**
     * Basic normalization from raw provider category.
     */
    public static TransactionCategory fromRaw(String rawCategory) {
        if (rawCategory == null) return OTHER;

        String normalized = rawCategory.toLowerCase();

        if (normalized.contains("food")) return FOOD;
        if (normalized.contains("travel")) return TRAVEL;
        if (normalized.contains("shop")) return SHOPPING;
        if (normalized.contains("entertainment")) return ENTERTAINMENT;
        if (normalized.contains("utility")) return UTILITIES;
        if (normalized.contains("health")) return HEALTH;
        if (normalized.contains("education")) return EDUCATION;
        if (normalized.contains("transfer")) return TRANSFER;
        if (normalized.contains("income")) return INCOME;
        if (normalized.contains("invest")) return INVESTMENT;
        if (normalized.contains("tax")) return TAX;
        if (normalized.contains("rent")) return RENT;
        if (normalized.contains("grocery")) return GROCERIES;
        if (normalized.contains("transport")) return TRANSPORT;
        if (normalized.contains("insurance")) return INSURANCE;
        if (normalized.contains("subscription")) return SUBSCRIPTION;
        if (normalized.contains("loan")) return LOAN;

        return OTHER;
    }
}