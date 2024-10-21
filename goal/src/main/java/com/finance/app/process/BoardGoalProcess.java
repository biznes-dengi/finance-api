package com.finance.app.process;

import com.finance.app.domain.Account;
import com.finance.app.service.AccountProcess;
import com.finance.app.boundary.request.BoardGoalRequest;
import com.finance.app.boundary.response.BalanceResponse;
import com.finance.app.boundary.response.BoardGoalResponse;
import com.finance.app.dao.BoardGoalDao;
import com.finance.app.domain.BoardGoal;
import com.finance.app.domain.enums.CurrencyCode;
import com.finance.app.exception.NotFoundException;
import com.finance.app.mapper.BalanceMapper;
import com.finance.app.mapper.BoardGoalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BoardGoalProcess {
    private final AccountProcess accountProcess;
    private final BoardGoalDao boardGoalDao;
    private final BoardGoalMapper boardGoalMapper;
    private final BalanceMapper balanceMapper;

    public int processGetOnlyId(int accountId) throws NotFoundException {
        return getBoardGoalByAccountId(accountId).getId();
    }

    public BalanceResponse processGetOnlyBalance(int boardGoalId) throws NotFoundException {
        final var response = getBoardGoalByBoardGoalId(boardGoalId);
        return balanceMapper.boardGoalToBalanceResponse(response);
    }

    public BoardGoalResponse processCreate(final BoardGoalRequest boardGoalRequest) {
        final var currentUser = accountProcess.getById(boardGoalRequest.accountId());
        final var response = boardGoalDao.createBoardGoal(initNewBoardGoalToSave(currentUser));
        return boardGoalMapper.boardGoalToBoardGoalResponse(response);
    }

    public BoardGoal updateBoardBalance(final int boardGoalId, final BigDecimal newValue) throws NotFoundException {
        final var boardGoalToUpdate = boardGoalDao.fetchBoardGoalById(boardGoalId);
        final var newValueOfBalance = boardGoalToUpdate.getBoardBalance().add(newValue);
        boardGoalToUpdate.setBoardBalance(newValueOfBalance);
        return boardGoalDao.createBoardGoal(boardGoalToUpdate);
    }

    BoardGoal getBoardGoalByAccountId(final int accountId) throws NotFoundException {
        checkIfAccountExists(accountId);
        return boardGoalDao.fetchBoardGoalByAccountId(accountId);
    }

    BoardGoal getBoardGoalByBoardGoalId(final int boardGoalId) throws NotFoundException {
        return boardGoalDao.fetchBoardGoalById(boardGoalId);
    }

    private BoardGoal initNewBoardGoalToSave(Account account) {
        final var initBalance = BigDecimal.ZERO;
        final CurrencyCode code = CurrencyCode.USD;
        return new BoardGoal(account, initBalance, code);
    }

    private void checkIfAccountExists(int accountId) throws NotFoundException {
        if (accountProcess.checkIfNotExists(accountId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(accountId));
        }
    }
}
