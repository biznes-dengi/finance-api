package com.maksyank.finance.user.boundary;

import com.maksyank.finance.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
}
