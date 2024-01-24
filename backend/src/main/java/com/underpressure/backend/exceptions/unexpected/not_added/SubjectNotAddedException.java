package com.underpressure.backend.exceptions.unexpected.not_added;

import com.underpressure.backend.exceptions.unexpected.UnexpectedException;

public class SubjectNotAddedException extends UnexpectedException {

    public SubjectNotAddedException() {
        super("Miski läks valesti ja uus aine jäi lisamata.");
    }

}
