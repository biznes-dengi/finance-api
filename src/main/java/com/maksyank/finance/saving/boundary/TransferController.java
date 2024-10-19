package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.TransactionTransferRequest;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.process.TransactionProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/board-savings/{boardSavingId}/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransactionProcess transactionProcess;

    @PostMapping
    @ResponseStatus(CREATED)
    public TransactionResponse saveTransactionTransfer(@RequestBody TransactionTransferRequest requestBody,
                                                       @PathVariable("boardSavingId") int boardSavingId) throws ParentException {
        return transactionProcess.processSaveTransactionTransfer(requestBody, boardSavingId);
    }
}
