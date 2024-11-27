package com.finance.app.goal.dao;

import com.finance.app.goal.domain.BoardGoal;
import com.finance.app.exception.NotFoundException;

public interface BoardGoalDao {

    /**
     * Create new BoardGoal record for the given accountId
     *
     * @param newBoardGoalToSave new BoardGoal to save
     * @return new created BoardGoal record
     */
    BoardGoal createBoardGoal(BoardGoal newBoardGoalToSave);

    /**
     * Fetch BoardGoal record by given accountId
     *
     * @param accountId unique identifier of Account entity
     * @return found BoardGoal record
     */
    BoardGoal fetchBoardGoalByAccountId(int accountId) throws NotFoundException;

    /**
     * Fetch BoardGoal record by given boardGoalId
     *
     * @param boardGoalId unique identifier of BoardGoal entity
     * @return found BoardGoal record
     */
    BoardGoal fetchBoardGoalById(int boardGoalId) throws NotFoundException;

    /**
     * Checks If the BoardGoal record exists for the given accountId
     *
     * @param accountId unique identifier of Account entity
     * @return true if exists, false otherwise
     */
    boolean existsBoardGoal(int accountId);
}
