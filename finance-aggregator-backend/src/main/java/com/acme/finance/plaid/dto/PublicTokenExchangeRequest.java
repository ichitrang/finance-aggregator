// plaid/dto/PublicTokenExchangeRequest.java
package com.acme.finance.plaid.dto;

import jakarta.validation.constraints.NotBlank;

public record PublicTokenExchangeRequest(
        @NotBlank String publicToken,
        String institutionName,
        String plaidAccountId,
        String accountName,
        String mask,
        String accountType,
        String accountSubtype
) {}