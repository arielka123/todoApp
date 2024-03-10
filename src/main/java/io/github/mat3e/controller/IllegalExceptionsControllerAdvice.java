package io.github.mat3e.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class )
public class IllegalExceptionsControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class) /// zmienia kod błedu z 500 na 404
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> handleStateException(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    }
}
