package com.underpressure.backend.exceptions.parameter;

public class BearerTokenParameterException extends ParameterException {

    public BearerTokenParameterException() {
        super("Päring ei sisaldanud id žetooni.");
    }

}
