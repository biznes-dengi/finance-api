package com.finance.app.domain;

import com.finance.app.domain.businessrules.InitRulesGoal;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.domain.enums.RiskProfileType;
import com.finance.app.domain.enums.GoalState;
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
@Table(name = "goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private GoalState state;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private CurrencyCode currency;

    @Column(name = "balance")
    private BigDecimal goalBalance;

    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "risk_profile")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private RiskProfileType riskProfile;

    @Embedded
    private ImageGoal image;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_change")
    private LocalDateTime lastChange;

    @ManyToOne
    @JoinColumn(name = "board_goal_id")
    private BoardGoal boardGoal;

    @OneToMany(mappedBy = "goal", fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;

    public Goal(InitRulesGoal initRulesGoal, String title, CurrencyCode currency,
                  BigDecimal targetAmount, LocalDate deadline, RiskProfileType riskProfile,
                  ImageGoal image, BoardGoal boardGoal
    ) {
        this.title = title;
        this.state = initRulesGoal.state();
        this.currency = currency;
        this.goalBalance = initRulesGoal.balance();
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.riskProfile = riskProfile;
        this.image = image;
        this.boardGoal = boardGoal;
    }

    public Goal(int id, String title, GoalState state, CurrencyCode currency, BigDecimal goalBalance,
                  BigDecimal targetAmount, LocalDate deadline, RiskProfileType riskProfile,
                  ImageGoal image, LocalDateTime createdOn, LocalDateTime lastChange, BoardGoal boardGoal
    ) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.currency = currency;
        this.goalBalance = goalBalance;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.riskProfile = riskProfile;
        this.image = image;
        this.createdOn = createdOn;
        this.lastChange = lastChange;
        this.boardGoal = boardGoal;
    }

    @Override
    public String toString() {
        return "Goal(id=" + this.getId() + ", name=" + this.getTitle() +
                ", state=" + this.getState() + ", currencyCode=" + this.getCurrency() +
                 ", balance=" + this.getGoalBalance() +
                ", targetAmount=" + this.getTargetAmount() + ", deadline=" + this.getDeadline() +
                ", riskProfile=" + this.getRiskProfile() + ", createdOn=" + this.getCreatedOn() +
                ", lastChange=" + this.getLastChange() + ", boardGoalId=" + this.getBoardGoal().getId() + ")";
    }
}
