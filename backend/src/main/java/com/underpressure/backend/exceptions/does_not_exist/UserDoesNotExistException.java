package com.underpressure.backend.exceptions.does_not_exist;

public class UserDoesNotExistException extends DoesNotExistException {

    public UserDoesNotExistException() {
        super("Sellist kasutajat pole leitud.");
    }

}
