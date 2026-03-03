// plaid/dto/TransactionsSyncResult.java
package com.acme.finance.plaid.dto;

public record TransactionsSyncResult(int inserted, int updated, int skipped) {}