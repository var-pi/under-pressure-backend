package com.underpressure.backend.services.database;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.underpressure.backend.exceptions.RequestException;

public interface Add {

    public void subjectInstance(Integer userId, String subjectUuid) throws RequestException;

    public void entry(Integer subjectInstanceId, Integer stressLevel) throws RequestException;

    public void user(Payload userInfo) throws RequestException;

}
