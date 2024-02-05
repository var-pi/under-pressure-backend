package com.underpressure.backend.exceptions.already_exists;

public class SubjectUnfollowedException extends AlreadyException {

    public SubjectUnfollowedException() {
        super("Seda ainet juba pole su ainete nimekirjas.");
    }

}
