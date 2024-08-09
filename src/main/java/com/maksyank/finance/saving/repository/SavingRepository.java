package com.maksyank.finance.saving.repository;

import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    Optional<Saving> findByIdAndUserAccount_Id(int financeId, int userId);
    Optional<List<Saving>> findByStateAndUserAccount_Id(SavingState status, int userId);
    Optional<List<Saving>> findByUserAccount_IdAndDeadlineNotNullAndState(int userId, SavingState status);
    boolean existsByIdAndUserAccount_Id(int financeId, int userId);
}
