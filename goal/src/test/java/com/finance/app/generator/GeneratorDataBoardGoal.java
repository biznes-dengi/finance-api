package com.finance.app.goal.goal.generator;

import com.finance.app.goal.domain.Account;
import com.finance.app.goal.domain.BoardGoal;
import com.finance.app.goal.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public class GeneratorDataBoardGoal {

    public static BoardGoal getTestData_testUpdateBoardBalance_01_02_03() {
        var account = new Account();
        account.setId(1);
        return new BoardGoal(account, BigDecimal.ZERO, CurrencyCode.USD);
    }
}
