package com.finance.app.boundary.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.app.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        int id,
        TransactionType type,
        String description,
        @JsonProperty("date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime transactionTimestamp,
        Integer fromIdGoal,
        Integer toIdGoal,
        BigDecimal amount
) { }
