package com.finance.app.config;

import com.finance.app.exception.AuthException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice(name = "authExcHandler")
//TODO: SOLVE WHY THIS EXCEPTION HANDLER DOESN'T HANDLE EXCEPTIONS
public class ExcHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorMessage> handleExc(final AuthException exc) {
        return ResponseEntity.status(exc.getStatus())
                .body(ErrorMessage.builder()
                        .status(HttpStatus.resolve(exc.getStatus().value()))
                        .message(exc.getMessage())
                        .cause(exc.getClass())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorMessage handleExc(final MethodArgumentNotValidException exc) {
        return ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exc.getMessage())
                .cause(exc.getClass())
                .build();
    }

    @Getter
    @Builder
    @Jacksonized
    public static class ErrorMessage {

        @Builder.Default
        private LocalDateTime date = LocalDateTime.now();
        private HttpStatus status;
        private String message;
        private Class<? extends Throwable> cause;
    }
}
