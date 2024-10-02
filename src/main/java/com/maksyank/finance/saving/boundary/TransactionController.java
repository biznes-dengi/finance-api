package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.boundary.response.TransactionAllResponse;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.process.TransactionProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("board-savings/{boardSavingId}/savings/{savingId}/transactions")
public class TransactionController {
    private final TransactionProcess transactionProcess;

    @GetMapping
    public TransactionAllResponse getAll(@PathVariable("savingId") int savingId,
                                         @RequestParam("pageNumber") int pageNumber,
                                         @PathVariable("boardSavingId") int boardSavingId) throws ParentException {
        return this.transactionProcess.processGetAll(savingId, pageNumber, boardSavingId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public StateOfSavingResponse save(@PathVariable("savingId") int savingId,
                                      @RequestBody TransactionRequest requestToSave,
                                      @PathVariable("boardSavingId") int boardSavingId) throws ParentException {
        return this.transactionProcess.processSave(requestToSave, savingId, boardSavingId);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getById(@PathVariable("savingId") int savingId,
                                       @PathVariable("transactionId") int depositId,
                                       @PathVariable("boardSavingId") int boardSavingId) throws ParentException {
        return this.transactionProcess.processGetById(depositId, savingId, boardSavingId);
    }

    @PatchMapping("/{transactionId}")
    public TransactionResponse update(@PathVariable("savingId") int savingId,
                       @PathVariable("transactionId") int depositId,
                       @PathVariable("boardSavingId") int boardSavingId,
                       @RequestBody TransactionUpdateRequest requestToUpdate) throws ParentException {
        return this.transactionProcess.processUpdate(depositId, savingId, requestToUpdate, boardSavingId);
    }
}
