package com.finance.app.boundary;

import com.finance.app.boundary.request.TransactionTransferRequest;
import com.finance.app.boundary.response.transaction.TransactionTransferResponse;
import com.finance.app.exception.ParentException;
import com.finance.app.process.TransactionProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/board-goals/{boardGoalId}/transfer")
@RequiredArgsConstructor
public class TransferController implements GoalBoundaryMetadata {
    private final TransactionProcess transactionProcess;

    @PostMapping
    @ResponseStatus(CREATED)
    public TransactionTransferResponse saveTransactionTransfer(@RequestBody TransactionTransferRequest requestBody,
                                                               @PathVariable("boardGoalId") int boardGoalId) throws ParentException {
        return transactionProcess.processSaveTransactionTransfer(requestBody, boardGoalId);
    }
}
