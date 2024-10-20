package com.finance.app.domain.dto;

import jakarta.validation.constraints.Size;

public record TransactionUpdateDto(
        @Size(max = 100, message = "The 'description' field must contain less than [100] symbols.")
        String description
) { }
