package com.underpressure.backend.exceptions;

import org.springframework.http.HttpStatus;

public class RequestException extends Exception {
    HttpStatus httpStatus;

    public RequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
