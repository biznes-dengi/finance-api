package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.request.BoardSavingRequest;
import com.maksyank.finance.saving.boundary.response.BalanceResponse;
import com.maksyank.finance.saving.boundary.response.BoardSavingResponse;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.process.BoardSavingProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
