package com.showmejobs.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message, HttpStatus status) {
        super(message);
    }
}
