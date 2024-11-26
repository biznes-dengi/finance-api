package com.finance.app.goal.domain.dto;

import com.finance.app.goal.domain.dto.base.BaseTransactionDto;
import com.finance.app.validation.step.transaction.DecimalScale;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionTransferDto extends BaseTransactionDto {

    @NotNull
    private LocalDateTime transactionTimestamp;

    @NotNull
    private Integer fromIdGoal;

    @NotNull @DecimalScale(2) @Positive
    private BigDecimal fromGoalAmount;

    @NotNull
    private Integer toIdGoal;

    @NotNull @DecimalScale(2) @Positive
    private BigDecimal toGoalAmount;

    public TransactionTransferDto(String description, LocalDateTime transactionTimestamp, Integer fromIdGoal, BigDecimal fromGoalAmount, Integer toIdGoal, BigDecimal toGoalAmount) {
        super(description);
        this.transactionTimestamp = transactionTimestamp;
        this.fromIdGoal = fromIdGoal;
        this.fromGoalAmount = fromGoalAmount;
        this.toIdGoal = toIdGoal;
        this.toGoalAmount = toGoalAmount;
    }

    public TransactionTransferDto(LocalDateTime transactionTimestamp, Integer fromIdGoal, BigDecimal fromGoalAmount, Integer toIdGoal, BigDecimal toGoalAmount) {
        this.transactionTimestamp = transactionTimestamp;
        this.fromIdGoal = fromIdGoal;
        this.fromGoalAmount = fromGoalAmount;
        this.toIdGoal = toIdGoal;
        this.toGoalAmount = toGoalAmount;
    }
}
