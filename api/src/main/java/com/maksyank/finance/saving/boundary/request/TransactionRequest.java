package com.maksyank.finance.saving.boundary.request;

import com.maksyank.finance.saving.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        TransactionType type,
        String description,
        LocalDateTime fundingDate,
        BigDecimal amount
) { }
