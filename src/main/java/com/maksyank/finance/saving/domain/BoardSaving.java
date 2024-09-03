package com.maksyank.finance.saving.domain;

import com.maksyank.finance.user.domain.Account;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "balance_savings")
    private BigDecimal balanceSavings;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public BoardSaving(Account account) {
        this.account = account;
    }
}
