package com.underpressure.backend.services.database;

import com.underpressure.backend.exceptions.RequestException;

public interface Update {

    void entry(Integer entryId, Integer stressLevel) throws RequestException;

}