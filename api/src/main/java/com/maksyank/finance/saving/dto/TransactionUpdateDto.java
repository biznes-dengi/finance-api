package com.maksyank.finance.saving.dto;

import jakarta.validation.constraints.Size;

public record TransactionUpdateDto(
        @Size(max = 100)
        String description
) { }
