package org.javaacademy.onlineBank.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OnlineBankControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleInternalException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("У нас произошла ошибка, уже работаем над ней!");
    }
}
