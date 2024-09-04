package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.boundary.response.BoardSavingResponse;
import com.maksyank.finance.saving.dao.BoardSavingDao;
import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.mapper.BoardSavingMapper;
import com.maksyank.finance.user.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardSavingProcess {
    private final AccountProcess accountProcess;
    private final BoardSavingDao boardSavingDao;
    private final BoardSavingMapper boardSavingMapper;

    public BoardSavingResponse processGetByAccountId(final int accountId) throws NotFoundException {
        checkIfUserExists(accountId);
        try {
            final var response =  boardSavingDao.fetchBoardSaving(accountId);
            return boardSavingMapper.boardSavingToBoardSavingResponse(response);
        } catch (NotFoundException e) {
            final var currentUser = accountProcess.getById(accountId);
            final var newBoardSaving = new BoardSaving(currentUser);
            final var response = boardSavingDao.createBoardSaving(newBoardSaving);
            return boardSavingMapper.boardSavingToBoardSavingResponse(response);
        }
    }

    private void checkIfUserExists(int accountId) throws NotFoundException {
        if (accountProcess.checkIfNotExists(accountId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(accountId));
        }
    }
}
