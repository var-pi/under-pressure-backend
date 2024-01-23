package com.underpressure.backend.exceptions.parameter;

public class SubjectNameParameterException extends ParameterException {

    public SubjectNameParameterException() {
        super("Päring ei sisaldanud aine nimetust.");
    }

}
