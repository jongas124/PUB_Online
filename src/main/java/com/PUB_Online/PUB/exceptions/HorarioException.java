package com.PUB_Online.PUB.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class HorarioException  extends RuntimeException{
    public HorarioException(String message) {
        super(message);
    }
    
}
