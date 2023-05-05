package com.template.data.exception;

public class CidadeNotFoundException extends RuntimeException {
    public CidadeNotFoundException(String message) {
        super(message);
    }
}
