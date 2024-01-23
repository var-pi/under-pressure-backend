package com.underpressure.backend.exceptions.does_not_exist;

public class TodaysEntryDoesNotExistException extends DoesNotExistException {

    public TodaysEntryDoesNotExistException(String message) {
        super("Täna pole sa veel selle aine kohta sissekannet teinud.");
    }

}
