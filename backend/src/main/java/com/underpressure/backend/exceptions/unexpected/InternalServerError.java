package com.underpressure.backend.exceptions.unexpected;

public class InternalServerError extends UnexpectedException {

    public InternalServerError() {
        super("Palume vabandust. Miski läks valesti.");
    }

}
