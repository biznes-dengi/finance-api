package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.SavingMapper;
import com.maksyank.finance.saving.service.process.SavingProcess;
import com.maksyank.finance.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// TODO remove bullshit impl as getting all time users (will fix when will realize security model)
// TODO delete end-point must have id user maybe (check when realize security)
// TODO for update & save will be better use not check  just get\find
// TODO think about toSave \ toUpdate (refactor) (naming)
// TODO refactor error handling
// TODO refactor user error handling
// TODO A check user isn't necessary because you can just get that user and will see if it exists
// TODO validation if need
@RestController
@RequestMapping("/saving")
public class SavingController {
    private SavingProcess savingProcess;
    private UserAccountService userAccountService;

    @Autowired
    SavingController(SavingProcess savingProcess, UserAccountService userAccountService) {
        this.savingProcess = savingProcess;
        this.userAccountService = userAccountService;
    }

    @GetMapping()
    public List<SavingViewResponse> getByState(
            @RequestParam("state") SavingState state,
            @RequestParam("userId") int userId
    ) {
        this.checkIfUserExists(userId);
        try {
            return this.savingProcess.processGetByState(state, userId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping()
    public ResponseEntity save(@RequestParam("userId") int userId, @RequestBody SavingRequest toSaveRequest) {
        this.checkIfUserExists(userId);
        try {
            final var user = this.userAccountService.getById(userId);
            this.savingProcess.processSave(toSaveRequest, user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DbOperationException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{savingId}")
    public SavingResponse getById(
            @PathVariable("savingId") int savingId,
            @RequestParam("userId") int userId
    ) {
        this.checkIfUserExists(userId);
        try {
            return this.savingProcess.processGetById(savingId, userId);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{savingId}")
    public ResponseEntity update(
            @PathVariable("savingId") int savingId,
            @RequestParam("userId") int userId,
            @RequestBody SavingRequest savingDtoToSave
    ) {
        this.checkIfUserExists(userId);
        try {
            final var user = userAccountService.getById(userId);
            this.savingProcess.processUpdate(savingId, savingDtoToSave, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (DbOperationException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{savingId}")
    public ResponseEntity delete(@PathVariable("savingId") int financeGoalId, @RequestParam("userId") int userId) {
        this.checkIfUserExists(userId);
        try {
            this.savingProcess.processDelete(financeGoalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DbOperationException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    private void checkIfUserExists(int userId) {
        if (!this.userAccountService.checkIfExists(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity 'User' not found by attribute 'id' = " + userId);
        }
    }
}
