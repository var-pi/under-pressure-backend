package com.underpressure.backend.exceptions.parameter;

public class StressLevelParameterException extends ParameterException {

    public StressLevelParameterException() {
        super("Päring ei sisaldanud stressi taset.");
    }

}
