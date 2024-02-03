package com.underpressure.backend.exceptions.parameter;

public class CodeParameterException extends ParameterException {

    public CodeParameterException() {
        super("Pärin ei sisaldanud kasutaja tuvastamiseks vajalikku koodi.");
    }

}
