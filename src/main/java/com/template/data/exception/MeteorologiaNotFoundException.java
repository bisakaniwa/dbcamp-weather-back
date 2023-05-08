package com.template.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeteorologiaNotFoundException extends RuntimeException {
    public MeteorologiaNotFoundException(String message) {
        super(message);
    }
}
