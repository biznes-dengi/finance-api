package com.finance.app.repository;

import com.finance.app.domain.BoardSaving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingBoardRepository extends JpaRepository<BoardSaving, Integer> {

    Optional<BoardSaving> findByAccount_Id(int accountId);

    Optional<BoardSaving> findById(int boardSavingId);

    boolean existsByAccount_Id(int accountId);
}
