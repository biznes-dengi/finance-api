package com.finance.app.generator;

import com.finance.app.domain.Account;
import com.finance.app.domain.BoardGoal;
import com.finance.app.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public class GeneratorDataBoardGoal {

    public static BoardGoal getTestData_testUpdateBoardBalance_01_02_03() {
        var account = new Account();
        account.setId(1);
        return new BoardGoal(account, BigDecimal.ZERO, CurrencyCode.USD);
    }
}
