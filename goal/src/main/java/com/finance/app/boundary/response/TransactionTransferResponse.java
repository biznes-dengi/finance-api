package com.finance.app.boundary.response;

import com.finance.app.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionTransferResponse(
        int id,
        TransactionType type,
        LocalDateTime transactionTimestamp,
        String description,
        Integer fromIdGoal,
        BigDecimal fromGoalAmount,
        Integer toIdGoal,
        BigDecimal toGoalAmount
) { }
