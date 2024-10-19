package com.maksyank.finance.utility.generator;

import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.account.domain.Account;
import com.maksyank.finance.saving.domain.enums.CurrencyCode;

import java.math.BigDecimal;

public class GeneratorDataBoardSaving {

    public static BoardSaving getTestData_testUpdateBoardBalance_01_02_03() {
        var account = new Account();
        account.setId(1);
        return new BoardSaving(account, BigDecimal.ZERO, CurrencyCode.USD);
    }
}
