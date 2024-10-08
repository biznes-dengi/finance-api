package com.maksyank.finance.saving.boundary.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maksyank.finance.saving.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        TransactionType type,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dealDate,
        BigDecimal amount
) { }
