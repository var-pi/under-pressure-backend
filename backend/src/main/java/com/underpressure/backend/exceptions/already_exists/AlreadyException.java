package com.underpressure.backend.exceptions.already_exists;

import org.springframework.http.HttpStatus;

import com.underpressure.backend.exceptions.RequestException;

public class AlreadyException extends RequestException {

    public AlreadyException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
