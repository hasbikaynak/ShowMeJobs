package com.showmejobs.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException{
    public ConflictException(String message, HttpStatus status) {
        super(message);
    }
}
