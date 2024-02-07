package com.underpressure.backend.exceptions.auth;

public class UserVerificationException extends AuthException {

    public UserVerificationException() {
        super("Kasutaja tunnuse kinnitamine ebaõnnestus.");
    }

}
