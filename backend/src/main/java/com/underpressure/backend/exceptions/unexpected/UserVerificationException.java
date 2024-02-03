package com.underpressure.backend.exceptions.unexpected;

public class UserVerificationException extends UnexpectedException {

    public UserVerificationException() {
        super("Kasutaja tunnuse kinnitamine ebaõnnestus.");
    }

}
