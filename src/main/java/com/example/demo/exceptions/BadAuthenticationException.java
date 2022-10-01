package com.example.demo.exceptions;

public class BadAuthenticationException extends RuntimeException{
    public BadAuthenticationException(String message) {
        super(message);
    }
}
