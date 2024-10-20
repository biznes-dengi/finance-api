package com.finance.app.boundary;

import com.finance.app.boundary.request.BoardSavingRequest;
import com.finance.app.boundary.response.BalanceResponse;
import com.finance.app.boundary.response.BoardSavingResponse;
import com.finance.app.exception.NotFoundException;
import com.finance.app.process.BoardSavingProcess;
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
@RequestMapping("/board-savings")
@RequiredArgsConstructor
public class BoardSavingController {
    private final BoardSavingProcess boardSavingProcess;

    @GetMapping("/id")
    public int getOnlyId(@RequestParam("accountId") int accountId) throws NotFoundException {
        return boardSavingProcess.processGetOnlyId(accountId);
    }

    @GetMapping("/balance")
    public BalanceResponse getOnlyBalance(@RequestParam("boardSavingId") int boardSavingId) throws NotFoundException {
        return boardSavingProcess.processGetOnlyBalance(boardSavingId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public BoardSavingResponse create(@RequestBody BoardSavingRequest boardSavingToSave) {
        return boardSavingProcess.processCreate(boardSavingToSave);
    }
}
