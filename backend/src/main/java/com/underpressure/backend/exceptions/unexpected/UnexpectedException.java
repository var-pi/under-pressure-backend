package com.underpressure.backend.exceptions.unexpected;

import org.springframework.http.HttpStatus;

import com.underpressure.backend.exceptions.RequestException;

public class UnexpectedException extends RequestException {

    public UnexpectedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
