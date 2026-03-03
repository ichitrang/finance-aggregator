// plaid/dto/LinkAccountResponse.java
package com.acme.finance.plaid.dto;

import java.util.UUID;

public record LinkAccountResponse(UUID bankAccountId, String status) {}