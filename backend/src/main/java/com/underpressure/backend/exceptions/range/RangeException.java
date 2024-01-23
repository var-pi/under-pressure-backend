package com.underpressure.backend.exceptions.range;

import org.springframework.http.HttpStatus;

import com.underpressure.backend.exceptions.RequestException;

public class RangeException extends RequestException {

    public RangeException(String message) {
        super(message, HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

}
