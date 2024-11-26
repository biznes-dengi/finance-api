package com.finance.app.goal.domain.dto;

import com.finance.app.goal.domain.dto.base.BaseTransactionDto;
import com.finance.app.goal.domain.enums.TransactionType;
import com.finance.app.validation.step.transaction.DecimalScale;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends BaseTransactionDto {
    @NotNull
    private TransactionType type;

    private LocalDateTime transactionTimestamp;

    @NotNull @DecimalScale(2) @Positive
    private BigDecimal amount;

    public TransactionDto(String description, TransactionType type, LocalDateTime transactionTimestamp, BigDecimal amount) {
        super(description);
        this.type = type;
        this.transactionTimestamp = transactionTimestamp;
        this.amount = amount;
    }

    public TransactionDto(TransactionType type, LocalDateTime transactionTimestamp, BigDecimal amount) {
        this.type = type;
        this.transactionTimestamp = transactionTimestamp;
        this.amount = amount;
    }
}