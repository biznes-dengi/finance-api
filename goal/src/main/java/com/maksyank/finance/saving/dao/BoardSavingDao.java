package com.maksyank.finance.saving.dao;

import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.saving.exception.NotFoundException;

public interface BoardSavingDao {

    /**
     * Create new BoardSaving record for the given accountId
     *
     * @param newBoardSavingToSave new BoardSaving to save
     * @return new created BoardSaving record
     */
    BoardSaving createBoardSaving(BoardSaving newBoardSavingToSave);

    /**
     * Fetch BoardSaving record by given accountId
     *
     * @param accountId unique identifier of Account entity
     * @return found BoardSaving record
     */
    BoardSaving fetchBoardSavingByAccountId(int accountId) throws NotFoundException;

    /**
     * Fetch BoardSaving record by given boardSavingId
     *
     * @param boardSavingId unique identifier of BoardSaving entity
     * @return found BoardSaving record
     */
    BoardSaving fetchBoardSavingById(int boardSavingId) throws NotFoundException;

    /**
     * Checks If the BoardSaving record exists for the given accountId
     *
     * @param accountId unique identifier of Account entity
     * @return true if exists, false otherwise
     */
    boolean existsBoardSaving(int accountId);
}
