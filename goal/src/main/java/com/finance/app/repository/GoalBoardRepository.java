package com.finance.app.repository;

import com.finance.app.domain.BoardGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoalBoardRepository extends JpaRepository<BoardGoal, Integer> {

    Optional<BoardGoal> findByAccount_Id(int accountId);

    Optional<BoardGoal> findById(int boardGoalId);

    boolean existsByAccount_Id(int accountId);
}
