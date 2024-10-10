package com.maksyank.finance.saving.dao.impl;

import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.repository.SavingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

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
    public List<Saving> fetchSavingsByStateAndDeadlineIsNotNull(SavingState state, int boardSavingId) {
        return savingRepository
                .findByBoardSaving_IdAndDeadlineNotNullAndState(boardSavingId, state)
                .orElse(Collections.emptyList());
    }

    @Override
    public Saving fetchSavingById(int savingId, int boardSavingId) throws NotFoundException {
        return savingRepository
                .findByIdAndBoardSaving_Id(savingId, boardSavingId)
                .orElseThrow(() -> new NotFoundException("Entity 'Saving' not found by attribute 'id' = " + savingId));
    }

    @Override
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
