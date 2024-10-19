package com.maksyank.finance.saving.boundary.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionTransferRequest(
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        Integer fromIdGoal,
        Integer toIdGoal,
        BigDecimal fromGoalAmount
) { }
