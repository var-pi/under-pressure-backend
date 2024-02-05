package com.underpressure.backend.controllers.classes.abstracts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.underpressure.backend.exceptions.RequestException;

public abstract class AuthenticatedPostController<S, T> extends Controller {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    protected String clientId;

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<S> handle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody T requestData) throws RequestException;

}
