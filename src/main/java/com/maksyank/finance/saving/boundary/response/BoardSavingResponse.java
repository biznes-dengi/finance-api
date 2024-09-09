package com.maksyank.finance.saving.boundary.response;

import java.math.BigDecimal;

public record BoardSavingResponse(
        int boardSavingId,
        BigDecimal boardBalance
) {}
