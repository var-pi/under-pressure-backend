package com.underpressure.backend.exceptions.range;

public class StressLevelRangeException extends RangeException {

    public StressLevelRangeException() {
        super("Stressi tase väärtus peab jääma lõiku [0,100].");
    }

}
