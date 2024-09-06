package com.maksyank.finance.saving.boundary.request;

import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

public record DepositAmountRequest(int savingId,
                                   @Min(value = 1970, message = "The 'year' field must contain value more than [1970].") int year,
                                   @Range(min = 1, max = 12, message = "The 'month' field must contain value between [1,12].") int month,
                                   int boardSavingId) {
}
