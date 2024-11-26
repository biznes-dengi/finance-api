package com.finance.app.goal.boundary;

import com.finance.app.goal.boundary.request.GoalRequest;
import com.finance.app.goal.boundary.response.GoalAllResponse;
import com.finance.app.goal.boundary.response.GoalResponse;
import com.finance.app.goal.domain.enums.GoalState;
import com.finance.app.goal.process.GoalProcess;
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

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

// TODO delete end-point must have id user maybe (check when realize security)
// TODO for update & save will be better use not check  just get\find
// TODO think about toSave \ toUpdate (refactor) (naming)
@RestController
@RequestMapping("board-goals/{boardGoalId}/goals")
@RequiredArgsConstructor
public class GoalController implements GoalBoundaryMetadata {
    private final GoalProcess goalProcess;

    @GetMapping
    public GoalAllResponse get(
            @PathVariable("boardGoalId") int boardGoalId,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam(value = "status", required = false) GoalState state) throws ParentException {
        if (state == null)
            return goalProcess.processGetAll(boardGoalId, pageNumber);
        else
            return goalProcess.processGetByState(state, boardGoalId, pageNumber);
    }

    @GetMapping("/{goalId}")
    public GoalResponse getById(@PathVariable("goalId") int goalId,
                                @PathVariable("boardGoalId") int boardGoalId,
                                Principal principal) throws ParentException {
        return goalProcess.processGetById(goalId, boardGoalId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public GoalResponse save(@PathVariable("boardGoalId") int boardGoalId,
                     @RequestBody GoalRequest toSaveRequest) throws ParentException {
        return goalProcess.processSave(toSaveRequest, boardGoalId);
    }

    @PutMapping("/{goalId}")
    public GoalResponse update(@PathVariable("goalId") int goalId,
                       @PathVariable("boardGoalId") int boardGoalId,
                       @RequestBody GoalRequest goalToSave) throws ParentException {
        return goalProcess.processUpdate(goalId, goalToSave, boardGoalId);
    }

    @DeleteMapping("/{goalId}")
    public void delete(
            @PathVariable("boardGoalId") int boardGoalId,
            @PathVariable("goalId") int goalId) throws ParentException {
        goalProcess.processDelete(goalId, boardGoalId);
    }
}
