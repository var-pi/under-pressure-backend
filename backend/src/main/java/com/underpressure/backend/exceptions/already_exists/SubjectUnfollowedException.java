package com.underpressure.backend.exceptions.already_exists;

public class SubjectUnfollowedException extends AlreadyException {

    public SubjectUnfollowedException() {
        super("Seda ainet pole su ainete nimekirjas.");
    }

}
