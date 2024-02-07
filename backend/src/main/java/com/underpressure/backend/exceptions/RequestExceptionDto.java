package com.underpressure.backend.exceptions;

import org.springframework.http.HttpStatus;

public class RequestExceptionDto {
    private HttpStatus statusMessage;
    private Integer statusCode;
    private String message;

    public RequestExceptionDto(RequestException ex) {
        this.statusMessage = ex.getHttpStatus();
        this.statusCode = ex.getHttpStatus().value();
        this.message = ex.getMessage();
    }

    public HttpStatus getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(HttpStatus statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
