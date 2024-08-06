package com.PUB_Online.PUB.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MenuException extends RuntimeException {
    public MenuException(String message) {
        super(message);
    }
}
