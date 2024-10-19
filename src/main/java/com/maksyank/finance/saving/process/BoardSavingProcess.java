package com.maksyank.finance.saving.process;

import com.maksyank.finance.account.domain.Account;
import com.maksyank.finance.saving.boundary.request.BoardSavingRequest;
import com.maksyank.finance.saving.boundary.response.BalanceResponse;
import com.maksyank.finance.saving.boundary.response.BoardSavingResponse;
import com.maksyank.finance.saving.dao.BoardSavingDao;
import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.saving.domain.enums.CurrencyCode;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.mapper.BalanceMapper;
import com.maksyank.finance.saving.mapper.BoardSavingMapper;
import com.maksyank.finance.account.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BoardSavingProcess {
    private final AccountProcess accountProcess;
    private final BoardSavingDao boardSavingDao;
    private final BoardSavingMapper boardSavingMapper;
    private final BalanceMapper balanceMapper;

    public int processGetOnlyId(int accountId) throws NotFoundException {
        return getBoardSavingByAccountId(accountId).getId();
    }

    public BalanceResponse processGetOnlyBalance(int boardSavingId) throws NotFoundException {
        final var response = getBoardSavingByBoardSavingId(boardSavingId);
        return balanceMapper.boardSavingToBalanceResponse(response);
    }

    public BoardSavingResponse processCreate(final BoardSavingRequest boardSavingRequest) {
        final var currentUser = accountProcess.getById(boardSavingRequest.accountId());
        final var response = boardSavingDao.createBoardSaving(initNewBoardSavingToSave(currentUser));
        return boardSavingMapper.boardSavingToBoardSavingResponse(response);
    }

    public BoardSaving updateBoardBalance(final int boardSavingId, final BigDecimal newValue) throws NotFoundException {
        final var boardSavingToUpdate = boardSavingDao.fetchBoardSavingById(boardSavingId);
        final var newValueOfBalance = boardSavingToUpdate.getBoardBalance().add(newValue);
        boardSavingToUpdate.setBoardBalance(newValueOfBalance);
        return boardSavingDao.createBoardSaving(boardSavingToUpdate);
    }

    BoardSaving getBoardSavingByAccountId(final int accountId) throws NotFoundException {
        checkIfAccountExists(accountId);
        return boardSavingDao.fetchBoardSavingByAccountId(accountId);
    }

    BoardSaving getBoardSavingByBoardSavingId(final int boardSavingId) throws NotFoundException {
        return boardSavingDao.fetchBoardSavingById(boardSavingId);
    }

    private BoardSaving initNewBoardSavingToSave(Account account) {
        final var initBalance = BigDecimal.ZERO;
        final CurrencyCode code = CurrencyCode.USD;
        return new BoardSaving(account, initBalance, code);
    }

    private void checkIfAccountExists(int accountId) throws NotFoundException {
        if (accountProcess.checkIfNotExists(accountId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(accountId));
        }
    }
}
