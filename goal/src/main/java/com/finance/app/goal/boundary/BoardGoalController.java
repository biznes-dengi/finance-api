package com.finance.app.goal.boundary;

import com.finance.app.goal.boundary.response.BalanceResponse;
import com.finance.app.goal.boundary.response.BoardGoalResponse;
import com.finance.app.goal.process.BoardGoalProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/board-goals")
@RequiredArgsConstructor
public class BoardGoalController implements GoalBoundaryMetadata {
    private final BoardGoalProcess boardGoalProcess;

    @GetMapping("/id")
    public int getOnlyId(@RequestParam("accountId") int accountId) throws ParentException {
        return boardGoalProcess.processGetOnlyId(accountId);
    }

    @GetMapping("/balance")
    public BalanceResponse getOnlyBalance(@RequestParam("boardGoalId") int boardGoalId) throws ParentException {
        return boardGoalProcess.processGetOnlyBalance(boardGoalId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public BoardGoalResponse create(@RequestBody BoardGoalRequest boardGoalToSave) throws ParentException {
        return boardGoalProcess.processCreate(boardGoalToSave);
    }
}
