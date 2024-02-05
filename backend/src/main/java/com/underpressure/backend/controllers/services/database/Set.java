package com.underpressure.backend.controllers.services.database;

import com.underpressure.backend.exceptions.RequestException;

public interface Set {

    void toFollow(Integer subjectInstanceId) throws RequestException;

    void toNotFollow(Integer subjectInstanceId) throws RequestException;

}