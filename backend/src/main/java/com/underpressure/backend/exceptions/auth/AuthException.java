package com.underpressure.backend.exceptions.auth;

import org.springframework.http.HttpStatus;

import com.underpressure.backend.exceptions.RequestException;

public class AuthException extends RequestException {

    public AuthException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}
