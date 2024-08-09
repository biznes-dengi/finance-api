package com.maksyank.finance.saving.service.persistence;

import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.repository.SavingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SavingPersistence {
    private final SavingRepository savingRepository;

    @Autowired
    SavingPersistence(SavingRepository savingRepository) {
        this.savingRepository = savingRepository;
    }

    public Saving findByIdAndUserId(int id, int userId) throws NotFoundException {
        return this.savingRepository
                .findByIdAndUserAccount_Id(id, userId)
                .orElseThrow(() -> new NotFoundException("Entity 'Finance Goal' not found by attribute 'id' = " + id));
    }

    public List<Saving> findByStateAndUserId(SavingState state, int userId) throws NotFoundException {
        return this.savingRepository
                .findByStateAndUserAccount_Id(state, userId)
                .orElseThrow(
                        () -> new NotFoundException("Entities 'Finance Goal' not found by attribute 'state' = " + state)
                );
    }

    public List<Saving> findByUserIdAndStateAndIfDeadlineIsNotNull(SavingState state, int userId) {
        return this.savingRepository
                .findByUserAccount_IdAndDeadlineNotNullAndState(userId, state)
                .orElse(Collections.emptyList());
    }

    // TODO refactor handling type
    public void save(Saving preparedToPersist) throws DbOperationException {
        try {
            this.savingRepository.save(preparedToPersist);
        } catch (Exception e) {
            throw new DbOperationException(e.getMessage(), e);
        }
    }

    // TODO refactor handling type
    public void deleteById(int id) throws DbOperationException {
        try {
            this.savingRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbOperationException(e.getMessage(), e);
        }
    }

    // TODO to think about how we could refactor, because some methods which call that method
    // TODO have to throw new exception if it returns false. It's a little bit stupid.
    // TODO Maybe just throw new exception if false
    public boolean ifExistsByIdAndUserId(int financeGoalId, int userId) {
        return this.savingRepository.existsByIdAndUserAccount_Id(financeGoalId, userId);
    }

}
