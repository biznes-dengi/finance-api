package com.finance.app.dao.impl;

import com.finance.app.dao.GoalDao;
import com.finance.app.domain.Goal;
import com.finance.app.domain.enums.GoalState;
import com.finance.app.exception.InternalError;
import com.finance.app.exception.NotFoundException;
import com.finance.app.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalDaoImpl implements GoalDao {
    private final GoalRepository goalRepository;
    @Value("${goal.batch-size}")
    private int goalBatchSize;

    @Override
    public Goal createGoal(Goal newGoalToSave) {
        return goalRepository.save(newGoalToSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<Goal> fetchGoalsByState(GoalState state, int boardGoalId, int pageNumber) throws NotFoundException {
        final var response =
                goalRepository.findByStateAndBoardGoal_Id(
                        state,
                        boardGoalId,
                        PageRequest.of(pageNumber, goalBatchSize)
                );

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("No 'Goal' records were found relative to 'boardGoalId' = "
                    + boardGoalId + " and by 'pageNumber' = " + pageNumber + " and by 'state' = " + state.name());
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Goal> fetchGoalsByStateAndDeadlineIsNotNull(GoalState state, int boardGoalId) {
        return goalRepository
                .findByBoardGoal_IdAndDeadlineNotNullAndState(boardGoalId, state)
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional(readOnly = true)
    public Goal fetchGoalById(int goalId, int boardGoalId) throws NotFoundException, InternalError {
        final var response = goalRepository
                .findById(goalId)
                .orElseThrow(() -> new NotFoundException("Record Goal not found by ID = " + goalId));

        if (response.getBoardGoal().getId() != boardGoalId)
            throw new InternalError("Something went wrong. The goal doesn't belong of the boardGoalId: " + boardGoalId);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<Goal> fetchAllGoals(int boardGoalId, int pageNumber) throws NotFoundException {
        final var response =
                goalRepository.findAllByBoardGoal_Id(boardGoalId, PageRequest.of(pageNumber, goalBatchSize));

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("No 'Goal' records were found relative to 'boardGoalId' = "
                    + boardGoalId + " and by 'pageNumber' = " + pageNumber);
        }
        return response;
    }

    @Override
    public void deleteGoal(int goalId) {
        goalRepository.deleteById(goalId);
    }


    // TODO to think about how we could refactor, because some methods which call that method
    // TODO have to throw new exception if it returns false. It's a little bit stupid.
    // TODO Maybe just throw new exception if false
    @Override
    public boolean existsGoal(int goalId, int boardGoalId) {
        return goalRepository.existsByIdAndBoardGoal_Id(goalId, boardGoalId);
    }


}
