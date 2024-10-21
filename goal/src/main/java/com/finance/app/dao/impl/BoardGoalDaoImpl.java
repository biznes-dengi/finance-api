package com.finance.app.dao.impl;

import com.finance.app.dao.BoardGoalDao;
import com.finance.app.domain.BoardGoal;
import com.finance.app.exception.NotFoundException;
import com.finance.app.repository.GoalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardGoalDaoImpl implements BoardGoalDao {
    private final GoalBoardRepository goalBoardRepository;

    @Override
    public BoardGoal createBoardGoal(BoardGoal toSave) {
        return goalBoardRepository.save(toSave);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardGoal fetchBoardGoalByAccountId(int accountId) throws NotFoundException {
       return goalBoardRepository.findByAccount_Id(accountId)
               .orElseThrow(() -> new NotFoundException("Entity 'BoardGoal' not found by attribute 'accountId' = " + accountId));
    }

    @Override
    @Transactional(readOnly = true)
    public BoardGoal fetchBoardGoalById(int boardGoalId) throws NotFoundException {
        return goalBoardRepository.findById(boardGoalId)
                .orElseThrow(() -> new NotFoundException("Entity 'BoardGoal' not found by attribute 'boardGoalId' = " + boardGoalId));
    }

    @Override
    public boolean existsBoardGoal(int accountId) {
        return goalBoardRepository.existsByAccount_Id(accountId);
    }
}
