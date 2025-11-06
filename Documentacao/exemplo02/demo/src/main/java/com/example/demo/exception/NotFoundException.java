package com.example.demo.exception;

/**
 * Exceção de domínio para recursos não encontrados (HTTP 404).
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
