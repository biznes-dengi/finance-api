package com.finance.app.domain;

import com.finance.app.domain.enums.CurrencyCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;

@Data
@Table(name = "board_goal")
@Entity
@NoArgsConstructor
public class BoardGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_goal_id")
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

    public BoardGoal(Account account, BigDecimal boardBalance, CurrencyCode currency) {
        this.account = account;
        this.boardBalance = boardBalance;
        this.currency = currency;
    }
}
