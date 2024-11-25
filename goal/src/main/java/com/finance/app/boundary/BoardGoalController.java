package com.finance.app.boundary;

import com.finance.app.boundary.request.BoardGoalRequest;
import com.finance.app.boundary.response.BalanceResponse;
import com.finance.app.boundary.response.BoardGoalResponse;
import com.finance.app.exception.NotFoundException;
import com.finance.app.exception.ParentException;
import com.finance.app.process.BoardGoalProcess;
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
    public int getOnlyId(@RequestParam("accountId") int accountId) throws NotFoundException {
        return boardGoalProcess.processGetOnlyId(accountId);
    }

    @GetMapping("/balance")
    public BalanceResponse getOnlyBalance(@RequestParam("boardGoalId") int boardGoalId) throws NotFoundException {
        return boardGoalProcess.processGetOnlyBalance(boardGoalId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public BoardGoalResponse create(@RequestBody BoardGoalRequest boardGoalToSave) throws ParentException {
        return boardGoalProcess.processCreate(boardGoalToSave);
    }
}
