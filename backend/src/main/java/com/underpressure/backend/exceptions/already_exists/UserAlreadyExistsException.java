package com.underpressure.backend.exceptions.already_exists;

public class UserAlreadyExistsException extends AlreadyException {

    public UserAlreadyExistsException() {
        super("Selline kasutaja on juba olemas.");
    }

}
