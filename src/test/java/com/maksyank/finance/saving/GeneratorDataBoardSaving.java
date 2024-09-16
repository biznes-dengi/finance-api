package com.maksyank.finance.saving;

import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.account.domain.Account;

import java.math.BigDecimal;

public class GeneratorDataBoardSaving {

    public static BoardSaving getTestData_testUpdateBoardBalance_01_02_03() {
        var account = new Account();
        account.setId(1);
        var boardSaving = new BoardSaving(account);
        boardSaving.setBoardBalance(BigDecimal.ZERO);
        return boardSaving;
    }
}
