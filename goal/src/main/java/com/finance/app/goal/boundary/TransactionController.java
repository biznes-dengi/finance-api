package com.finance.app.goal.boundary;

import com.finance.app.goal.boundary.request.TransactionRequest;
import com.finance.app.goal.boundary.request.TransactionUpdateRequest;
import com.finance.app.goal.boundary.response.transaction.TransactionAllResponse;
import com.finance.app.goal.boundary.response.transaction.TransactionResponse;
import com.finance.app.goal.exception.ParentException;
import com.finance.app.goal.process.TransactionProcess;
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
@RequestMapping("board-goals/{boardGoalId}/goals/{goalId}/transactions")
public class TransactionController implements GoalBoundaryMetadata {
    private final TransactionProcess transactionProcess;

    @GetMapping
    public TransactionAllResponse getAll(@PathVariable("goalId") int goalId,
                                         @RequestParam("pageNumber") int pageNumber,
                                         @PathVariable("boardGoalId") int boardGoalId) throws ParentException {
        return transactionProcess.processGetAll(goalId, pageNumber, boardGoalId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TransactionResponse save(@PathVariable("goalId") int goalId,
                                    @RequestBody TransactionRequest requestToSave,
                                    @PathVariable("boardGoalId") int boardGoalId) throws ParentException {
        return transactionProcess.processSave(requestToSave, goalId, boardGoalId);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getById(@PathVariable("goalId") int goalId,
                                             @PathVariable("transactionId") int depositId,
                                             @PathVariable("boardGoalId") int boardGoalId) throws ParentException {
        return transactionProcess.processGetById(depositId, goalId, boardGoalId);
    }

    @PatchMapping("/{transactionId}")
    public TransactionResponse update(@PathVariable("goalId") int goalId,
                                            @PathVariable("transactionId") int depositId,
                                            @PathVariable("boardGoalId") int boardGoalId,
                                            @RequestBody TransactionUpdateRequest requestToUpdate) throws ParentException {
        return transactionProcess.processUpdate(depositId, goalId, requestToUpdate, boardGoalId);
    }
}
