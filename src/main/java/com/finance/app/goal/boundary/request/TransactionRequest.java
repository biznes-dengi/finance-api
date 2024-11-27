package com.finance.app.goal.boundary.request;

import com.finance.app.goal.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        TransactionType type,
        String description,
        LocalDateTime date,
        BigDecimal amount
) { }
