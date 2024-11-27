package com.finance.app.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finance.app.exception.AuthException;
import com.finance.app.exception.ParentException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
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

    @ExceptionHandler(ParentException.class)
    public ResponseEntity<ErrorMessage> handleExc(final ParentException exc) {
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
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private LocalDateTime date = LocalDateTime.now();
        private HttpStatus status;
        private String message;
        private Class<? extends Throwable> cause;
    }
}