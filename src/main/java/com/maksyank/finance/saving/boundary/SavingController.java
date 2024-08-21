package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.service.process.SavingProcess;
import com.maksyank.finance.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

// TODO remove bullshit impl as getting all time users (will fix when will realize security model)
// TODO delete end-point must have id user maybe (check when realize security)
// TODO for update & save will be better use not check  just get\find
// TODO think about toSave \ toUpdate (refactor) (naming)
// TODO A check user isn't necessary because you can just get that user and will see if it exists
@RestController
@RequestMapping("/saving")
@RequiredArgsConstructor
public class SavingController {

    private final SavingProcess savingProcess;
    private final UserAccountService userAccountService;

    @GetMapping
    public List<SavingViewResponse> getByState(@RequestParam("state") SavingState state,
                                               @RequestParam("userId") int userId) throws ParentException {
        this.checkIfUserExists(userId);
        return this.savingProcess.processGetByState(state, userId);
    }

    @GetMapping("/{savingId}")
    public SavingResponse getById(@PathVariable("savingId") int savingId,
                                  @RequestParam("userId") int userId) throws ParentException {
        this.checkIfUserExists(userId);
        return this.savingProcess.processGetById(savingId, userId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void save(@RequestParam("userId") int userId,
                     @RequestBody SavingRequest toSaveRequest) throws ParentException {
        this.checkIfUserExists(userId);
        final var user = this.userAccountService.getById(userId);
        this.savingProcess.processSave(toSaveRequest, user);
    }

    @PutMapping("/{savingId}")
    public void update(@PathVariable("savingId") int savingId,
                       @RequestParam("userId") int userId,
                       @RequestBody SavingRequest savingDtoToSave) throws ParentException {
        this.checkIfUserExists(userId);
        final var user = userAccountService.getById(userId);
        this.savingProcess.processUpdate(savingId, savingDtoToSave, user);
    }

    @DeleteMapping("/{savingId}")
    public void delete(@PathVariable("savingId") int financeGoalId,
                       @RequestParam("userId") int userId) throws ParentException {
        this.checkIfUserExists(userId);
        this.savingProcess.processDelete(financeGoalId);
    }

    private void checkIfUserExists(int userId) throws NotFoundException {
        if (this.userAccountService.checkIfNotExists(userId)) {
            throw new NotFoundException("Entity 'User' not found by attribute 'id' = %s".formatted(userId));
        }
    }
}
