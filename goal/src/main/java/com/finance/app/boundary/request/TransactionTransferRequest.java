package com.finance.app.boundary.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionTransferRequest(
        String description,
        @JsonProperty("date")
        LocalDateTime transactionTimestamp,
        @JsonProperty("fromGoalId") Integer fromIdGoal,
        BigDecimal fromGoalAmount,
        @JsonProperty("toGoalId") Integer toIdGoal,
        BigDecimal toGoalAmount
) { }
