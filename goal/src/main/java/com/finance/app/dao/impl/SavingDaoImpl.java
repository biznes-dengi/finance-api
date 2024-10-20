package com.finance.app.dao.impl;

import com.finance.app.dao.SavingDao;
import com.finance.app.domain.Saving;
import com.finance.app.domain.enums.SavingState;
import com.finance.app.exception.InternalError;
import com.finance.app.exception.NotFoundException;
import com.finance.app.repository.SavingRepository;
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
public class SavingDaoImpl implements SavingDao {
    private final SavingRepository savingRepository;
    @Value("${saving.batch-size}")
    private int savingBatchSize;

    @Override
    public Saving createSaving(Saving newSavingToSave) {
        return savingRepository.save(newSavingToSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<Saving> fetchSavingsByState(SavingState state, int boardSavingId, int pageNumber) throws NotFoundException {
        final var response =
                savingRepository.findByStateAndBoardSaving_Id(
                        state,
                        boardSavingId,
                        PageRequest.of(pageNumber, savingBatchSize)
                );

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("No 'Saving' records were found relative to 'boardSavingId' = "
                    + boardSavingId + " and by 'pageNumber' = " + pageNumber + " and by 'state' = " + state.name());
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Saving> fetchSavingsByStateAndDeadlineIsNotNull(SavingState state, int boardSavingId) {
        return savingRepository
                .findByBoardSaving_IdAndDeadlineNotNullAndState(boardSavingId, state)
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional(readOnly = true)
    public Saving fetchSavingById(int savingId, int boardSavingId) throws NotFoundException, InternalError {
        final var response = savingRepository
                .findById(savingId)
                .orElseThrow(() -> new NotFoundException("Record Goal not found by ID = " + savingId));

        if (response.getBoardSaving().getId() != boardSavingId)
            throw new InternalError("Something went wrong. The goal doesn't belong of the boardSavingId: " + boardSavingId);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<Saving> fetchAllSavings(int boardSavingId, int pageNumber) throws NotFoundException {
        final var response =
                savingRepository.findAllByBoardSaving_Id(boardSavingId, PageRequest.of(pageNumber, savingBatchSize));

        if (response.getNumberOfElements() == 0) {
            throw new NotFoundException("No 'Saving' records were found relative to 'boardSavingId' = "
                    + boardSavingId + " and by 'pageNumber' = " + pageNumber);
        }
        return response;
    }

    @Override
    public void deleteSaving(int savingId) {
        savingRepository.deleteById(savingId);
    }


    // TODO to think about how we could refactor, because some methods which call that method
    // TODO have to throw new exception if it returns false. It's a little bit stupid.
    // TODO Maybe just throw new exception if false
    @Override
    public boolean existsSaving(int savingId, int boardSavingId) {
        return savingRepository.existsByIdAndBoardSaving_Id(savingId, boardSavingId);
    }


}
