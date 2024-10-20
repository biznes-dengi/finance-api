package com.finance.app.boundary;

import com.finance.app.boundary.request.SavingRequest;
import com.finance.app.boundary.response.SavingAllResponse;
import com.finance.app.boundary.response.SavingResponse;
import com.finance.app.domain.enums.SavingState;
import com.finance.app.exception.ParentException;
import com.finance.app.process.SavingProcess;
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

import static org.springframework.http.HttpStatus.CREATED;

// TODO delete end-point must have id user maybe (check when realize security)
// TODO for update & save will be better use not check  just get\find
// TODO think about toSave \ toUpdate (refactor) (naming)
@RestController
@RequestMapping("board-savings/{boardSavingId}/savings")
@RequiredArgsConstructor
public class SavingController {
    private final SavingProcess savingProcess;

    @GetMapping
    public SavingAllResponse get(
            @PathVariable("boardSavingId") int boardSavingId,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam(value = "status", required = false) SavingState state) throws ParentException {
        if (state == null)
            return savingProcess.processGetAll(boardSavingId, pageNumber);
        else
            return savingProcess.processGetByState(state, boardSavingId, pageNumber);
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
                       @RequestBody SavingRequest savingToSave) throws ParentException {
        return savingProcess.processUpdate(savingId, savingToSave, boardSavingId);
    }

    @DeleteMapping("/{savingId}")
    public void delete(@PathVariable("savingId") int savingId) {
        savingProcess.processDelete(savingId);
    }
}
