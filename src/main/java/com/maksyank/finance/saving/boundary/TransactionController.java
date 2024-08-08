package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.service.process.TransactionProcess;
import com.maksyank.finance.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// TODO maybe refactor checkIfExists finGoal, probably move to service, think about it
// TODO A check user isn't necessary because you can just get that user and will see if it exists
// TODO validation if need
@RestController
@RequestMapping("/saving/{savingId}/transaction")
public class TransactionController {
    private final UserAccountService userAccountService;
    private final TransactionProcess transactionProcess;

    @Autowired
    TransactionController(TransactionProcess transactionProcess, UserAccountService userAccountService) {
        this.transactionProcess = transactionProcess;
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public List<TransactionViewResponse> getByPage(
            @PathVariable("savingId") int savingId,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("userId") int userId
    ) {
        this.checkIfUserExists(userId);
        try {
            return this.transactionProcess.processGetByPage(savingId, pageNumber, userId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping
    public StateOfSavingResponse save(
            @PathVariable("savingId") int savingId,
            @RequestBody TransactionRequest requestToSave,
            @RequestParam("userId") int userId
    ) {
        this.checkIfUserExists(userId);
        try {
            return this.transactionProcess.processSave(TransactionMapper.mapToDto(requestToSave), savingId, userId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (DbOperationException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getById(
            @PathVariable("savingId") int savingId,
            @PathVariable("transactionId") int depositId,
            @RequestParam("userId") int userId
    ) {
        this.checkIfUserExists(userId);
        try {
            return this.transactionProcess.processGetById(depositId, savingId, userId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    // TODO add validation to description
    @PatchMapping("/{transactionId}")
    public ResponseEntity update(
            @PathVariable("savingId") int savingId,
            @PathVariable("transactionId") int depositId,
            @RequestParam("userId") int userId,
            @RequestBody TransactionUpdateRequest requestToUpdate
    ) {
        this.checkIfUserExists(userId);
        try {
            this.transactionProcess.processUpdate(depositId, savingId, TransactionMapper.mapToUpdateDto(requestToUpdate), userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (DbOperationException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    private void checkIfUserExists(int userId) {
        if (!this.userAccountService.checkIfExists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity 'User' not found by attribute 'id' = " + userId);
        }
    }
}
