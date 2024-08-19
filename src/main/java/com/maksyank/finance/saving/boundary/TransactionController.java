package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.service.process.TransactionProcess;
import com.maksyank.finance.user.service.UserAccountService;
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

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

// TODO maybe refactor checkIfNotExists finGoal, probably move to service, think about it
// TODO A check user isn't necessary because you can just get that user and will see if it exists
@RestController
@RequiredArgsConstructor
@RequestMapping("/saving/{savingId}/transaction")
public class TransactionController {

    private final UserAccountService userAccountService;
    private final TransactionProcess transactionProcess;

    @GetMapping
    public List<TransactionViewResponse> getByPage(@PathVariable("savingId") int savingId,
                                                   @RequestParam("pageNumber") int pageNumber,
                                                   @RequestParam("userId") int userId) throws ParentException {
        this.checkIfUserExists(userId);
        return this.transactionProcess.processGetByPage(savingId, pageNumber, userId);
    }

    @PostMapping
    public StateOfSavingResponse save(@PathVariable("savingId") int savingId,
                                      @RequestBody TransactionRequest requestToSave,
                                      @RequestParam("userId") int userId) throws ParentException {
        this.checkIfUserExists(userId);
        return this.transactionProcess.processSave(requestToSave, savingId, userId);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getById(@PathVariable("savingId") int savingId,
                                       @PathVariable("transactionId") int depositId,
                                       @RequestParam("userId") int userId) throws ParentException {
        this.checkIfUserExists(userId);
        return this.transactionProcess.processGetById(depositId, savingId, userId);
    }

    @PatchMapping("/{transactionId}")
    @ResponseStatus(OK)
    public void update(@PathVariable("savingId") int savingId,
                       @PathVariable("transactionId") int depositId,
                       @RequestParam("userId") int userId,
                       @RequestBody TransactionUpdateRequest requestToUpdate) throws ParentException {
        this.checkIfUserExists(userId);
        this.transactionProcess.processUpdate(depositId, savingId, requestToUpdate, userId);
    }

    private void checkIfUserExists(int userId) throws NotFoundException {
        if (this.userAccountService.checkIfNotExists(userId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(userId));
        }
    }
}
