package com.underpressure.backend.services.database;

public interface Check {

    boolean subjectInstanceExists(Integer userId, String subjectUuid);

    boolean subjectInstanceFollowed(Integer subjectInstanceId);

    boolean entryExists(Integer subjectInstanceId);

    boolean userExists(Integer userId);

    boolean userWithGoogleSubExists(String googleSub);

}