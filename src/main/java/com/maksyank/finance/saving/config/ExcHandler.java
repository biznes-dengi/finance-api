package com.maksyank.finance.saving.config;

import com.maksyank.finance.saving.exception.ParentException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ExcHandler {

    @ExceptionHandler(ParentException.class)
    public ResponseEntity<ErrorMessage> handleExc(final ParentException exc) {
        return ResponseEntity.status(exc.getStatus())
                .body(ErrorMessage.builder()
                        .status(HttpStatus.resolve(exc.getStatus().value()))
                        .message(exc.getMessage())
                        .cause(exc.getClass())
                        .build());
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
