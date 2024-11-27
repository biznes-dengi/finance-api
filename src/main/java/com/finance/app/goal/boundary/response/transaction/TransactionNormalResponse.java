package com.finance.app.goal.boundary.response.transaction;

import com.finance.app.goal.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TransactionNormalResponse extends TransactionResponse {
        private BigDecimal amount;

        public TransactionNormalResponse(int id, String description, TransactionType type, LocalDateTime transactionTimestamp, BigDecimal amount) {
                super(id, description, type, transactionTimestamp);
                this.amount = amount;
        }
}
