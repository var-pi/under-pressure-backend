package com.underpressure.backend.exceptions;

import org.springframework.http.HttpStatus;

public class RequestExceptionDto {
    private HttpStatus httpStatus;
    private String message;

    public RequestExceptionDto(RequestException ex) {
        this.httpStatus = ex.getHttpStatus();
        this.message = ex.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
