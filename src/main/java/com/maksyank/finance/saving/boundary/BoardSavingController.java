package com.maksyank.finance.saving.boundary;

import com.maksyank.finance.saving.boundary.response.BoardSavingResponse;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.process.BoardSavingProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board-saving")
@RequiredArgsConstructor
public class BoardSavingController {
    private final BoardSavingProcess boardSavingProcess;

    @GetMapping
    public BoardSavingResponse getByAccountId(@RequestParam("accountId") int accountId) throws NotFoundException {
        return boardSavingProcess.processGetByAccountId(accountId);
    }
    @GetMapping("/{boardSavingId}")
    public BoardSavingResponse getGetByBoardSavingId(
            @PathVariable("boardSavingId") int boardSavingId,
            @RequestParam("accountId") int accountId
    ) throws NotFoundException {
        return boardSavingProcess.processGetByBoardSavingId(boardSavingId, accountId);
    }
}
