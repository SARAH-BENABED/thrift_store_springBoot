package com.example.demo;

import org.springframework.http.HttpStatus ;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.* ;
import org.springframework.web.server.ResponseStatusException ;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason()) ;
    }
}

