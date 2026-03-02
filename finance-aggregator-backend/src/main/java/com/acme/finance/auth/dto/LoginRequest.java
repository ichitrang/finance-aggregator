// auth/dto/LoginRequest.java
package com.acme.finance.auth.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
    @Email @NotBlank String email,
    @NotBlank String password
) {}