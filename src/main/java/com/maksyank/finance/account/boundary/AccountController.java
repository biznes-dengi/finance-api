package com.maksyank.finance.account.boundary;

import com.maksyank.finance.account.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountProcess accountProcess;
}
