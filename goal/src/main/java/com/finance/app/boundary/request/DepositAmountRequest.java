package com.finance.app.boundary.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

// TODO move validation annotations to DTO
public record DepositAmountRequest(
        int goalId,
        @Min(value = 1970, message = "The 'year' field must contain value more than [1970].")
        int year,
        @Min(value = 1, message = "The 'month' field must contain value between [1,12].")
        @Max(value = 12, message = "The 'month' field must contain value between [1,12].")
        int month,
        int boardGoalId
) { }
