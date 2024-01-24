package com.underpressure.backend.exceptions.parameter;

import org.springframework.http.HttpStatus;

import com.underpressure.backend.exceptions.RequestException;

public class ParameterException extends RequestException {

    public ParameterException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
