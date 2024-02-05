package com.underpressure.backend.exceptions.parameter;

public class CodeParameterException extends ParameterException {

    public CodeParameterException() {
        super("Päring ei sisaldanud kasutaja tuvastamiseks vajalikku koodi.");
    }

}
