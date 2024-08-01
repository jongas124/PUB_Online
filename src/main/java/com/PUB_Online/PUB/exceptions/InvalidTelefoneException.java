package com.PUB_Online.PUB.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidTelefoneException extends RuntimeException {
    public InvalidTelefoneException(String message) {
        super(message);
    }
    
}
