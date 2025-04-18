package com.underpressure.backend.services.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.underpressure.backend.exceptions.auth.UserVerificationException;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;

public interface FetchGoogle {

    public Payload userInfo(String idTokenString, String clientId) throws UserVerificationException;

    public String sub(String idTokenString, String clientId) throws UserVerificationException;

    public Integer userId(String idTokenString, String clientId)
            throws UserVerificationException, UserDoesNotExistException;

}
