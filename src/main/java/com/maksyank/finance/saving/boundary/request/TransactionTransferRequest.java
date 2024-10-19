package com.maksyank.finance.saving.boundary.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionTransferRequest(
        String description,
        LocalDateTime date,
        Integer fromIdGoal,
        Integer toIdGoal,
        BigDecimal fromGoalAmount
) { }
