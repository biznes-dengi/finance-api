package com.maksyank.finance.saving.dao;

import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.NotFoundException;

import java.util.List;

public interface SavingDao {

    /**
     * Create new Saving record from given newSavingToSave
     *
     * @param newSavingToSave new Saving for save
     * @return new created Saving record
     */
    Saving createSaving(Saving newSavingToSave);

    /**
     * Fetch list of Saving record by state and boardSavingId
     *
     * @param state state of Saving entity
     * @param boardSavingId unique identifier of BoardSaving which Saving belongs
     * @return list of found Saving records
     */
    List<Saving> fetchSavingByState(SavingState state, int boardSavingId) throws NotFoundException;

    /**
     * Fetch list of Saving by state which has deadline is not NULL
     *
     * @param state state of Saving entity
     * @param boardSavingId unique identifier of BoardSaving which Saving belongs
     * @return list of found Saving records
     */
    List<Saving> fetchSavingByStateAndDeadlineIsNotNull(SavingState state, int boardSavingId);

    /**
     * Fetch Saving record by savingId and boardSavingId
     *
     * @param savingId unique identifier of Saving entity which must find
     * @param boardSavingId unique identifier of BoardSaving which Saving belongs
     * @return found Saving record
     */
    Saving fetchSavingById(int savingId, int boardSavingId) throws NotFoundException;

    /**
     * Delete Saving record by given savingId
     *
     * @param savingId unique identifier of Saving entity
     */
    void deleteSaving(int savingId);

    /**
     * Checks If the BoardSaving record exists for the given accountId
     *
     * @param savingId unique identifier of Saving entity which must find
     * @param boardSavingId unique identifier of BoardSaving which Saving belongs
     * @return true if exists, false otherwise
     */
    boolean existsSaving(int savingId, int boardSavingId);
}
