// auth/dto/RegisterRequest.java
package com.acme.finance.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @Email @NotBlank String email,
    @NotBlank @Size(min=8) String password,
    String fullName
) {}