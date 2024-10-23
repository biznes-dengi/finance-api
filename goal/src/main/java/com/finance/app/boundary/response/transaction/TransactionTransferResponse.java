package com.finance.app.boundary.response.transaction;

import com.finance.app.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor()
@EqualsAndHashCode(callSuper=true)
public class TransactionTransferResponse extends TransactionResponse {
    private Integer fromIdGoal;
    private BigDecimal fromGoalAmount;
    private String fromGoalName;
    private Integer toIdGoal;
    private BigDecimal toGoalAmount;
    private String toGoalName;

    public TransactionTransferResponse(int id, String description, TransactionType type, LocalDateTime transactionTimestamp, Integer fromIdGoal, BigDecimal fromGoalAmount, String fromGoalName, Integer toIdGoal, BigDecimal toGoalAmount, String toGoalName) {
        super(id, description, type, transactionTimestamp);
        this.fromIdGoal = fromIdGoal;
        this.fromGoalAmount = fromGoalAmount;
        this.fromGoalName = fromGoalName;
        this.toIdGoal = toIdGoal;
        this.toGoalAmount = toGoalAmount;
        this.toGoalName = toGoalName;
    }
}
