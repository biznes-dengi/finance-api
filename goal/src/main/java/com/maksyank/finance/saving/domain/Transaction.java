package com.maksyank.finance.saving.domain;

import com.maksyank.finance.saving.domain.enums.TransactionType;
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
    @Column(name = "deal_date")
    private LocalDateTime dealDate;
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "saving_id")
    private Saving saving;

    public Transaction(TransactionType type, String description, LocalDateTime dealDate, BigDecimal amount, Saving saving) {
        this.type = type;
        this.description = description;
        this.dealDate = dealDate;
        this.amount = amount;
        this.saving = saving;
    }

    @Override
    public String toString() {
        return "Transaction(id=" + this.getId() + ", type=" + this.getType() + ", description=" +
                this.getDescription() + ", dealDate=" + this.getDealDate() + ", amount=" +
                this.getAmount() + ", savingId=" + this.getSaving().getId() + ")";
    }
}
