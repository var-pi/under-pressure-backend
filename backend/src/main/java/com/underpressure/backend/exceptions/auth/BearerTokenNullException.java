package com.underpressure.backend.exceptions.auth;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Päring ei sisaldanud isikut tuvastavat päist.")
public class BearerTokenNullException extends AuthException {

    public BearerTokenNullException() {
        super("Päring ei sisaldanud isikut tuvastavat päist.");
    }

}
