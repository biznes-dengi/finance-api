package com.finance.app.goal.dao;

import com.finance.app.goal.domain.Goal;
import com.finance.app.goal.domain.enums.GoalState;
import com.finance.app.goal.exception.NotFoundException;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface GoalDao {

    /**
     * Create new Goal record from given newGoalToSave
     *
     * @param newGoalToSave new Goal for save
     * @return new created Goal record
     */
    Goal createGoal(Goal newGoalToSave);

    /**
     * Fetch list of Goals by state. Method is pageable.
     *
     * @param state state of Goal entity
     * @param boardGoalId unique identifier of BoardGoal which Goal belongs
     * @return list of found Goal records
     */
    Slice<Goal>  fetchGoalsByState(GoalState state, int boardGoalId, int pageNumber) throws NotFoundException;

    /**
     * Fetch list of Goal by state which has deadline is not NULL
     *
     * @param state state of Goal entity
     * @param boardGoalId unique identifier of BoardGoal which Goal belongs
     * @return list of found Goal records
     */
    List<Goal> fetchGoalsByStateAndDeadlineIsNotNull(GoalState state, int boardGoalId);

    /**
     * Fetch Goal record by goalId and boardGoalId
     *
     * @param goalId unique identifier of Goal entity which must find
     * @param boardGoalId unique identifier of BoardGoal which Goal belongs
     * @return found Goal record
     */
    Goal fetchGoalById(int goalId, int boardGoalId) throws NotFoundException, InternalError;

    /**
     * Fetch Slice of Goals by given boardGoalId
     *
     * @param boardGoalId unique identifier of BoardGoal which Goal belongs
     * @param pageNumber number of page with Goal
     * @return batch of Goals
     * @throws NotFoundException if Goals wasn't found by given boardGoalId and pageNumber
     */
    Slice<Goal> fetchAllGoals(int boardGoalId, int pageNumber) throws NotFoundException;

    /**
     * Delete Goal record by given goalId
     *
     * @param goalId unique identifier of Goal entity
     */
    void deleteGoal(int goalId);

    /**
     * Checks If the BoardGoal record exists for the given accountId
     *
     * @param goalId unique identifier of Goal entity which must find
     * @param boardGoalId unique identifier of BoardGoal which Goal belongs
     * @return true if exists, false otherwise
     */
    boolean existsGoal(int goalId, int boardGoalId);
}
