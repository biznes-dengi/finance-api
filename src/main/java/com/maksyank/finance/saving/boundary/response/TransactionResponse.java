package com.maksyank.finance.saving.boundary.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maksyank.finance.saving.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        int id,
        TransactionType type,
        String description,
        @JsonProperty("date")
        LocalDateTime transactionTimestamp,
        BigDecimal amount
) { }
