package com.finance.app.boundary;

import com.finance.app.boundary.request.TransactionTransferRequest;
import com.finance.app.boundary.response.TransactionResponse;
import com.finance.app.exception.ParentException;
import com.finance.app.process.TransactionProcess;
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
