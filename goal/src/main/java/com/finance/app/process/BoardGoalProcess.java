package com.finance.app.process;

import com.finance.app.domain.Account;
import com.finance.app.domain.enums.TransactionType;
import com.finance.app.exception.ParentException;
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

    public BoardGoal updateBoardBalance(
            final TransactionType type,
            final BigDecimal amount,
            final int boardGoalId
    ) throws ParentException {
        if (type == TransactionType.DEPOSIT)
            return calculateNewBoardBalance(boardGoalId, amount);
        else
            return calculateNewBoardBalance(boardGoalId, amount.multiply(BigDecimal.valueOf(-1)));
    }

    public BoardGoal calculateNewBoardBalance(final int boardGoalId, final BigDecimal amount)
            throws ParentException {
        final var boardGoal = boardGoalDao.fetchBoardGoalById(boardGoalId);
        final var newBalance = boardGoal.getBoardBalance().add(amount);
        boardGoal.setBoardBalance(newBalance);
        return boardGoalDao.createBoardGoal(boardGoal);
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
