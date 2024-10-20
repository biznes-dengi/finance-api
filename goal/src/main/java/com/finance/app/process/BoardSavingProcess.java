package com.finance.app.process;

import com.finance.app.domain.Account;
import com.finance.app.service.AccountProcess;
import com.finance.app.boundary.request.BoardSavingRequest;
import com.finance.app.boundary.response.BalanceResponse;
import com.finance.app.boundary.response.BoardSavingResponse;
import com.finance.app.dao.BoardSavingDao;
import com.finance.app.domain.BoardSaving;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.exception.NotFoundException;
import com.finance.app.mapper.BalanceMapper;
import com.finance.app.mapper.BoardSavingMapper;
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
