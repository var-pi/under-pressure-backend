package com.underpressure.backend.db;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Subject {
    @Setter
    @Getter
    public static class Title {
        private String en;
        private String et;

    }

    private String uuid;
    private String code;
    private int credits;
    private Title title;

    public Subject() {
    }
}