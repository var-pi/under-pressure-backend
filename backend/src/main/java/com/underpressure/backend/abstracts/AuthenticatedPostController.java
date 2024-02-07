package com.underpressure.backend.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.underpressure.backend.exceptions.RequestException;

public abstract class AuthenticatedPostController<S, T, V> extends AuthenticatedController {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<S> handle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody T requestBody,
            V requestPathVariables) throws RequestException;

}
