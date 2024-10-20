package com.finance.app.domain.dto;

import com.finance.app.domain.enums.TransactionType;
import com.finance.app.validation.step.transaction.DecimalScale;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        @NotNull TransactionType type,
        @Size(max = 100) String description,
        LocalDateTime transactionTimestamp,
        Integer fromIdGoal,
        Integer toIdGoal,
        @NotNull @DecimalScale(2) BigDecimal amount
) { }