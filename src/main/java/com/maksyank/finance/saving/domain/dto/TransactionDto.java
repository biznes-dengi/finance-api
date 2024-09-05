package com.maksyank.finance.saving.domain.dto;

import com.maksyank.finance.saving.domain.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        @NotNull
        TransactionType type,
        @Size(max = 100)
        String description,
        LocalDateTime dealDate,
        @NotNull
        BigDecimal amount
) { }
