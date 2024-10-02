package com.maksyank.finance.saving.process;

import com.maksyank.finance.account.domain.Account;
import com.maksyank.finance.saving.boundary.response.BoardSavingResponse;
import com.maksyank.finance.saving.dao.BoardSavingDao;
import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.mapper.BoardSavingMapper;
import com.maksyank.finance.account.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BoardSavingProcess {
    private final AccountProcess accountProcess;
    private final BoardSavingDao boardSavingDao;
    private final BoardSavingMapper boardSavingMapper;

    /**
     * Fetch BoardSaving. Must be using only when user don't know boardSavingId.
     *
     * @param accountId unique identifier of Account entity
     * @return BoardSavingResponse
     * @throws NotFoundException if nothing found
     */
    public BoardSavingResponse processGetByAccountId(final int accountId) throws NotFoundException {
        checkIfAccountExists(accountId);
        try {
            final var response =  boardSavingDao.fetchBoardSavingByAccountId(accountId);
            return boardSavingMapper.boardSavingToBoardSavingResponse(response);
        } catch (NotFoundException e) {
            final var currentUser = accountProcess.getById(accountId);
            final var response = boardSavingDao.createBoardSaving(createNewBoardSavingToSave(currentUser));
            return boardSavingMapper.boardSavingToBoardSavingResponse(response);
        }
    }

    /**
     * Fetch BoardSaving. Must be using for fetching balance of BoardSaving.
     * When user refresh page or something like that.
     *
     * @param boardSavingId unique identifier of BoardSaving entity
     * @param accountId unique identifier of Account entity
     * @return BoardSavingResponse
     * @throws NotFoundException if nothing found
     */
    @Transactional(readOnly = true)
    public BoardSavingResponse processGetByBoardSavingId(final int boardSavingId, final int accountId) throws NotFoundException {
        checkIfAccountExists(accountId);
        final var response = boardSavingDao.fetchBoardSavingById(boardSavingId);
        return boardSavingMapper.boardSavingToBoardSavingResponse(response);
    }

    public BoardSaving updateBoardBalance(final int boardSavingId, final BigDecimal newValue) throws NotFoundException {
        final var boardSavingToUpdate = boardSavingDao.fetchBoardSavingById(boardSavingId);
        final var newValueOfBalance = boardSavingToUpdate.getBoardBalance().add(newValue);
        boardSavingToUpdate.setBoardBalance(newValueOfBalance);
        return boardSavingDao.createBoardSaving(boardSavingToUpdate);
    }

    private BoardSaving createNewBoardSavingToSave(Account account) {
        final var initBalance = BigDecimal.ZERO;
        return new BoardSaving(account, initBalance);
    }

    private void checkIfAccountExists(int accountId) throws NotFoundException {
        if (accountProcess.checkIfNotExists(accountId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(accountId));
        }
    }
}
