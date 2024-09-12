package com.maksyank.finance.saving.repository;

import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    Optional<Saving> findByIdAndBoardSaving_Id(int savingId, int boardSavingId);
    Optional<List<Saving>> findByStateAndBoardSaving_Id(SavingState status, int boardSavingId);
    Optional<List<Saving>> findByBoardSaving_IdAndDeadlineNotNullAndState(int boardSavingId, SavingState status);
    Slice<Saving> findAllByBoardSaving_Id(int boardSavingId, Pageable pageable);
    boolean existsByIdAndBoardSaving_Id(int savingId, int boardSavingId);
}
