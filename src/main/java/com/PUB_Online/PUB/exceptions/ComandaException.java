package com.PUB_Online.PUB.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ComandaException extends RuntimeException {
    public ComandaException(String msg) {
        super(msg);
    }
    
}
