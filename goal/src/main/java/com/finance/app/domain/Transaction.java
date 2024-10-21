package com.finance.app.domain;

import com.finance.app.domain.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TransactionType type;
    @Column(name = "description")
    private String description;
    @Column(name = "transaction_timestamp")
    private LocalDateTime transactionTimestamp;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "from_goal_id")
    private Integer fromIdGoal;
    @Column(name = "to_goal_id")
    private Integer toIdGoal;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    public Transaction(int id, TransactionType type, String description, LocalDateTime transactionTimestamp, BigDecimal amount, Goal goal) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.transactionTimestamp = transactionTimestamp;
        this.amount = amount;
        this.fromIdGoal = null;
        this.toIdGoal = null;
        this.goal = goal;
    }

    public Transaction(TransactionType type, String description, LocalDateTime transactionTimestamp, BigDecimal amount, Integer fromIdGoal, Integer toIdGoal, Goal goal) {
        this.type = type;
        this.description = description;
        this.transactionTimestamp = transactionTimestamp;
        this.amount = amount;
        this.fromIdGoal = fromIdGoal;
        this.toIdGoal = toIdGoal;
        this.goal = goal;
    }

    public Transaction(TransactionType type, String description, LocalDateTime transactionTimestamp, BigDecimal amount, Goal goal) {
        this.type = type;
        this.description = description;
        this.transactionTimestamp = transactionTimestamp;
        this.amount = amount;
        this.fromIdGoal = null;
        this.toIdGoal = null;
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", transactionTimestamp=" + transactionTimestamp +
                ", fromGoalAmount=" + amount +
                ", fromIdGoal=" + fromIdGoal +
                ", toIdGoal=" + toIdGoal +
                ", goal=" + goal +
                '}';
    }
}
