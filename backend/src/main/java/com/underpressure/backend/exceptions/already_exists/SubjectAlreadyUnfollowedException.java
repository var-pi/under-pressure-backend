package com.underpressure.backend.exceptions.already_exists;

public class SubjectAlreadyUnfollowedException extends AlreadyException {

    public SubjectAlreadyUnfollowedException() {
        super("Seda ainet juba pole su ainete nimekirjas.");
    }

}
