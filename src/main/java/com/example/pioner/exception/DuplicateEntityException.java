package com.example.pioner.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
