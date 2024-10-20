package com.finance.app.domain;

import com.finance.app.domain.businessrules.InitRulesSaving;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RiskProfileType;
import com.finance.app.domain.enums.SavingState;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
@Table(name = "saving")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private SavingState state;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private CurrencyCode currency;

    @Column(name = "balance")
    private BigDecimal savingBalance;

    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "risk_profile")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private RiskProfileType riskProfile;

    @Embedded
    private ImageSaving image;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_change")
    private LocalDateTime lastChange;

    @ManyToOne
    @JoinColumn(name = "board_saving_id")
    private BoardSaving boardSaving;

    @OneToMany(mappedBy = "saving", fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;

    public Saving(InitRulesSaving initRulesSaving, String title, CurrencyCode currency,
                  BigDecimal targetAmount, LocalDate deadline, RiskProfileType riskProfile,
                  ImageSaving image, BoardSaving boardSaving
    ) {
        this.title = title;
        this.state = initRulesSaving.state();
        this.currency = currency;
        this.savingBalance = initRulesSaving.balance();
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.riskProfile = riskProfile;
        this.image = image;
        this.boardSaving = boardSaving;
    }

    public Saving(int id, String title, SavingState state, CurrencyCode currency, BigDecimal savingBalance,
                  BigDecimal targetAmount, LocalDate deadline, RiskProfileType riskProfile,
                  ImageSaving image, LocalDateTime createdOn, LocalDateTime lastChange, BoardSaving boardSaving
    ) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.currency = currency;
        this.savingBalance = savingBalance;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.riskProfile = riskProfile;
        this.image = image;
        this.createdOn = createdOn;
        this.lastChange = lastChange;
        this.boardSaving = boardSaving;
    }

    @Override
    public String toString() {
        return "Saving(id=" + this.getId() + ", name=" + this.getTitle() +
                ", state=" + this.getState() + ", currencyCode=" + this.getCurrency() +
                 ", balance=" + this.getSavingBalance() +
                ", targetAmount=" + this.getTargetAmount() + ", deadline=" + this.getDeadline() +
                ", riskProfile=" + this.getRiskProfile() + ", createdOn=" + this.getCreatedOn() +
                ", lastChange=" + this.getLastChange() + ", boardSavingId=" + this.getBoardSaving().getId() + ")";
    }
}
