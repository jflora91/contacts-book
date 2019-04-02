package com.mindera.graduate.contactsbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super("Error: " + message);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super("Error: " + message);
    }

}

