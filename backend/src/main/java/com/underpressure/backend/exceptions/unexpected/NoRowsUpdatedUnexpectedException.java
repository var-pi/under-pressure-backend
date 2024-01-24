package com.underpressure.backend.exceptions.unexpected;

public class NoRowsUpdatedUnexpectedException extends UnexpectedException {

    public NoRowsUpdatedUnexpectedException() {
        super("Miski läks valesti ja andmed polnud uuendatud.");
    }

}
