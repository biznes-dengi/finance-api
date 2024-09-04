package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.service.process.SavingProcess;
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
@RequestMapping("board-saving/{boardSavingId}/saving")
@RequiredArgsConstructor
public class SavingController {
    private final SavingProcess savingProcess;

    @GetMapping
    public List<SavingViewResponse> getByState(@RequestParam("state") SavingState state,
                                               @PathVariable("boardSavingId") int boardSavingId) throws ParentException {
        return savingProcess.processGetByState(state, boardSavingId);
    }

    @GetMapping("/{savingId}")
    public SavingResponse getById(@PathVariable("savingId") int savingId,
                                  @PathVariable("boardSavingId") int boardSavingId) throws ParentException {
        return savingProcess.processGetById(savingId, boardSavingId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public SavingResponse save(@PathVariable("boardSavingId") int boardSavingId,
                     @RequestBody SavingRequest toSaveRequest) throws ParentException {
        return savingProcess.processSave(toSaveRequest, boardSavingId);
    }

    @PutMapping("/{savingId}")
    public SavingResponse update(@PathVariable("savingId") int savingId,
                       @PathVariable("boardSavingId") int boardSavingId,
                       @RequestBody SavingRequest savingDtoToSave) throws ParentException {
        return savingProcess.processUpdate(savingId, savingDtoToSave, boardSavingId);
    }

    @DeleteMapping("/{savingId}")
    public void delete(@PathVariable("savingId") int savingId) throws ParentException {
        savingProcess.processDelete(savingId);
    }
}
