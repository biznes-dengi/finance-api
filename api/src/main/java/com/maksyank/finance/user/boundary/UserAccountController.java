package com.maksyank.finance.user.boundary;

import com.maksyank.finance.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    private UserAccountService userAccountService;

    @Autowired
    UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
