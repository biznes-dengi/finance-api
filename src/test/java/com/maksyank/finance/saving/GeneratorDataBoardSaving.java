package com.maksyank.finance.saving;

import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.account.domain.Account;

import java.math.BigDecimal;

public class GeneratorDataBoardSaving {

    public static BoardSaving getTestData_testUpdateBoardBalance_01_02_03() {
        var account = new Account();
        account.setId(1);
        return new BoardSaving(account, BigDecimal.ZERO);
    }
}
