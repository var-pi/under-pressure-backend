package com.underpressure.backend.exceptions.does_not_exist;

import org.springframework.http.HttpStatus;

import com.underpressure.backend.exceptions.RequestException;

public class DoesNotExistException extends RequestException {

    public DoesNotExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
