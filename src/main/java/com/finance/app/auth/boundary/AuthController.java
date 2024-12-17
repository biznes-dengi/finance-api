package com.finance.app.auth.boundary;

import com.finance.app.auth.boundary.request.LoginRequest;
import com.finance.app.auth.boundary.request.RegisterRequest;
import com.finance.app.auth.boundary.request.ValidationRequest;
import com.finance.app.auth.boundary.response.ValidationResponse;
import com.finance.app.auth.service.AuthService;
import com.finance.app.exception.DuplicationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Contains all endpoints related to the authentication/authorization flow")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public String generate(@RequestBody @Validated final LoginRequest request) {
        return service.loginUser(request);
    }

    @PostMapping("/validate")
    public ValidationResponse validate(@RequestBody @Validated final ValidationRequest request) {
        return service.validateToken(request);
    }

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public String save(@RequestBody @Validated final RegisterRequest request) throws DuplicationException {
        if (service.checkIfUserExist(request.email())) {
            throw new DuplicationException("User with this email [" + request.email() + "], already exists.");
        }
        return service.register(request);
    }
}
