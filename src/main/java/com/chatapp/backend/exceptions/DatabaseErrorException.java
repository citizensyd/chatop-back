package com.chatapp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException(String message) {
        super(message);
    }
}
