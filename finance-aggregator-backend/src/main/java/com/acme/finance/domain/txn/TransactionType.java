package com.acme.finance.domain.txn;

/**
 * Transaction types based on financial flow.
 */
public enum TransactionType {

    CARD,
    TRANSFER,
    ACH,
    WIRE,
    CASH,
    ATM,
    INTEREST,
    FEE,
    REFUND,
    DIRECT_DEPOSIT,
    UNKNOWN;

    public static TransactionType fromProvider(String providerType) {
        if (providerType == null) return UNKNOWN;

        String normalized = providerType.toLowerCase();

        if (normalized.contains("card")) return CARD;
        if (normalized.contains("transfer")) return TRANSFER;
        if (normalized.contains("ach")) return ACH;
        if (normalized.contains("wire")) return WIRE;
        if (normalized.contains("cash")) return CASH;
        if (normalized.contains("atm")) return ATM;
        if (normalized.contains("interest")) return INTEREST;
        if (normalized.contains("fee")) return FEE;
        if (normalized.contains("refund")) return REFUND;
        if (normalized.contains("deposit")) return DIRECT_DEPOSIT;

        return UNKNOWN;
    }
}