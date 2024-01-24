package com.underpressure.backend.exceptions;

import org.springframework.http.HttpStatus;

public class RequestException extends Exception {
    private HttpStatus httpStatus;

    public RequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
