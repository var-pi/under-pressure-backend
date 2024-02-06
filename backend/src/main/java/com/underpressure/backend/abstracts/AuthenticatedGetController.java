package com.underpressure.backend.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import com.underpressure.backend.exceptions.RequestException;

public abstract class AuthenticatedGetController<S, T> extends AuthenticatedController {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<S> handle(
            @RequestHeader("Authorization") String bearerToken,
            @ModelAttribute T params) throws RequestException;

}