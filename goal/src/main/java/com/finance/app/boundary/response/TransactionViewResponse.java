package com.finance.app.boundary.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.app.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionViewResponse(
        int id,
        TransactionType type,
        @JsonProperty("date")
        LocalDateTime transactionTimestamp,
        BigDecimal amount
) { }
