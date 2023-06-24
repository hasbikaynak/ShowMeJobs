package com.showmejobs.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
    }
}
