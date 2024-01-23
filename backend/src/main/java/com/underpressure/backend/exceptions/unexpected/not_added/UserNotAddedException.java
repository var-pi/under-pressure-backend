package com.underpressure.backend.exceptions.unexpected.not_added;

import com.underpressure.backend.exceptions.unexpected.UnexpectedException;

public class UserNotAddedException extends UnexpectedException {

    public UserNotAddedException() {
        super("Miski läks valesti ja uus kasutaja ei saanud loodud.");
    }

}
