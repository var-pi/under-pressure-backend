package com.underpressure.backend.exceptions;

import org.springframework.http.HttpStatus;

// ErrorResponseException
public class RequestException extends RuntimeException {

    private HttpStatus statusCode;

    public RequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.statusCode = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return statusCode;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.statusCode = httpStatus;
    }

}
