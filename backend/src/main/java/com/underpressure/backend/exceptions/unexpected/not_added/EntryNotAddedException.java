package com.underpressure.backend.exceptions.unexpected.not_added;

import com.underpressure.backend.exceptions.unexpected.UnexpectedException;

public class EntryNotAddedException extends UnexpectedException {

    public EntryNotAddedException() {
        super("Miski läks valesti ja uus sissekanne jäi lisamata.");
    }

}
