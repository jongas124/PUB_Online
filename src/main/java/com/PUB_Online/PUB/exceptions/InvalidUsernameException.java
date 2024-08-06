package com.PUB_Online.PUB.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUsernameException extends RuntimeException{
    public InvalidUsernameException(String message) {
        super(message);
    }
    
}
