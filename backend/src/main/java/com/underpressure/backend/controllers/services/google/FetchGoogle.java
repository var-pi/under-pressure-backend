package com.underpressure.backend.controllers.services.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;

public interface FetchGoogle {

    public Payload userInfo(String idTokenString, String clientId) throws UserVerificationException;

    public String sub(String idTokenString, String clientId) throws UserVerificationException;

    public Integer userId(String idTokenString, String clientId)
            throws UserVerificationException, UserDoesNotExistException;

}
