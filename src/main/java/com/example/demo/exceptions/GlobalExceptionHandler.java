package com.example.demo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadAuthenticationException.class)
    public ResponseEntity<String> badAuthenticationExceptionHandler(BadAuthenticationException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<String> registrationExceptionHandler(RegistrationException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<String> refreshTokenExpiredExceptionHandler(RefreshTokenExpiredException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }



}
