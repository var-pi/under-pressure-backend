package com.underpressure.backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.RequestExceptionDto;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<RequestExceptionDto> handleAuthenticationException(RequestException ex)
            throws JsonProcessingException {
        return new ResponseEntity<>(new RequestExceptionDto(ex), ex.getHttpStatus());
    }

}
