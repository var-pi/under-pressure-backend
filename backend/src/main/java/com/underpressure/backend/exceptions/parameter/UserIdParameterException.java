package com.underpressure.backend.exceptions.parameter;

public class UserIdParameterException extends ParameterException {

    public UserIdParameterException() {
        super("Päring ei sisaldanud kasutaja tunnust.");
    }

}
