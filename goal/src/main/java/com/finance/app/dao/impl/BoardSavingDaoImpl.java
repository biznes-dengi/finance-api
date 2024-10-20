package com.finance.app.dao.impl;

import com.finance.app.dao.BoardSavingDao;
import com.finance.app.domain.BoardSaving;
import com.finance.app.exception.NotFoundException;
import com.finance.app.repository.SavingBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardSavingDaoImpl implements BoardSavingDao {
    private final SavingBoardRepository savingBoardRepository;

    @Override
    public BoardSaving createBoardSaving(BoardSaving toSave) {
        return savingBoardRepository.save(toSave);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardSaving fetchBoardSavingByAccountId(int accountId) throws NotFoundException {
       return savingBoardRepository.findByAccount_Id(accountId)
               .orElseThrow(() -> new NotFoundException("Entity 'BoardSaving' not found by attribute 'accountId' = " + accountId));
    }

    @Override
    @Transactional(readOnly = true)
    public BoardSaving fetchBoardSavingById(int boardSavingId) throws NotFoundException {
        return savingBoardRepository.findById(boardSavingId)
                .orElseThrow(() -> new NotFoundException("Entity 'BoardSaving' not found by attribute 'boardSavingId' = " + boardSavingId));
    }

    @Override
    public boolean existsBoardSaving(int accountId) {
        return savingBoardRepository.existsByAccount_Id(accountId);
    }
}
