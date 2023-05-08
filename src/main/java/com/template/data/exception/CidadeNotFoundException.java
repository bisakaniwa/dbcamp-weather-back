package com.template.data.exception;

import org.webjars.NotFoundException;

public class CidadeNotFoundException extends NotFoundException {
    public CidadeNotFoundException(String message) {
        super(message);
    }
}
