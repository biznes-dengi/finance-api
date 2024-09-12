package com.maksyank.finance.user.boundary;

import com.maksyank.finance.user.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final AccountProcess accountProcess;
}
