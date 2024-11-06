package com.finance.app.boundary;

import com.finance.app.boundary.request.LoginRequest;
import com.finance.app.boundary.request.RegisterRequest;
import com.finance.app.boundary.request.ValidationRequest;
import com.finance.app.boundary.response.ValidationResponse;
import com.finance.app.service.AuthService;
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
public class AuthController implements AuthBoundaryMetadata {

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
    public String save(@RequestBody @Validated final RegisterRequest request) {
        return service.register(request);
    }
}
