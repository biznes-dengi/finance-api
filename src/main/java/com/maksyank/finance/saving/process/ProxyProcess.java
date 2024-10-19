package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ParentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Bridge between TransactionProcess and SavingProcess.
 * The bridge helps get access from one process to another.
 *
 */
@Service
@RequiredArgsConstructor
public class ProxyProcess {
    private final SavingProcess savingProcess;
    private final BoardSavingProcess boardSavingProcess;

    public Saving proxyToUpdateSavingBalance(final BigDecimal newValue, final int savingId, final int boardSavingId)
            throws ParentException {
        return savingProcess.updateSavingBalance(newValue, savingId, boardSavingId);
    }

    public BoardSaving proxyToUpdateBoardBalance(final int boardSavingId, final BigDecimal newValue)
            throws NotFoundException {
        return boardSavingProcess.updateBoardBalance(boardSavingId, newValue);
    }

    public List<Saving> proxyToUpdateSavingBalancesWhenDoTransferTransaction(int boardSavingId, int fromSavingId, int toSavingId, BigDecimal amount)
            throws ParentException {
        return savingProcess.updateSavingBalancesWhenDoTransferTransaction(boardSavingId, fromSavingId, toSavingId, amount);
    }
}
