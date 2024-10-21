package com.finance.app.repository;

import com.finance.app.domain.Goal;
import com.finance.app.domain.enums.GoalState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Slice<Goal> findByStateAndBoardGoal_Id(GoalState status, int boardGoalId, Pageable pageable);
    Optional<List<Goal>> findByBoardGoal_IdAndDeadlineNotNullAndState(int boardGoalId, GoalState status);
    Slice<Goal> findAllByBoardGoal_Id(int boardGoalId, Pageable pageable);
    boolean existsByIdAndBoardGoal_Id(int goalId, int boardGoalId);
}
