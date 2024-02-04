package com.underpressure.backend.exceptions.parameter;

public class IdTokenStringParameterException extends ParameterException {

    public IdTokenStringParameterException() {
        super("Päring ei sisaldanud id žetooni.");
    }

}
