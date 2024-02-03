package com.underpressure.backend.exceptions.unexpected;

public class AuthenticationFailedException extends UnexpectedException {

    public AuthenticationFailedException() {
        super("Kasutaja tuvastamine on ebaõnnestunud.");
    }

}
