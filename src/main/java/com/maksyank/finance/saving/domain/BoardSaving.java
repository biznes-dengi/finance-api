package com.maksyank.finance.saving.domain;

import com.maksyank.finance.account.domain.Account;
import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;

@Data
@Table(name = "board_saving")
@Entity
@NoArgsConstructor
public class BoardSaving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_saving_id")
    private int id;

    @Column(name = "balance")
    private BigDecimal boardBalance;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private CurrencyCode currency;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public BoardSaving(Account account, BigDecimal boardBalance, CurrencyCode currency) {
        this.account = account;
        this.boardBalance = boardBalance;
        this.currency = currency;
    }
}
