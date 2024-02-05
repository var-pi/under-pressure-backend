package com.underpressure.backend.controllers.services.database;

public interface Check {

    boolean subjectInstanceExists(Integer userId, Integer subjectId);

    boolean subjectInstanceFollowed(Integer subjectInstanceId);

    boolean entryExists(Integer subjectInstanceId);

    boolean userExists(Integer userId);

    boolean userWithGoogleSubExists(String googleSub);

}