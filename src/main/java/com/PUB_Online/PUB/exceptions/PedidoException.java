package com.PUB_Online.PUB.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class PedidoException extends RuntimeException {

    public PedidoException(String msg) {
        super(msg);
    }

    
}
