package com.underpressure.backend.services.database;

import com.underpressure.backend.exceptions.RequestException;

public interface Validate {

    void userId(Integer userId, boolean hasToExist) throws RequestException;

    void userId(Integer userId) throws RequestException;

    void subjectName(String subjectName) throws RequestException;

    void stressLevel(Integer stressLevel) throws RequestException;

    void isFollowed(Integer subjectInstanceId) throws RequestException;

    void isUnfollowed(Integer subjectInstanceId) throws RequestException;

    void userDoesNotExists(Integer userId) throws RequestException;

    void code(String code) throws RequestException;

    void bearerToken(String bearerToken) throws RequestException;

}