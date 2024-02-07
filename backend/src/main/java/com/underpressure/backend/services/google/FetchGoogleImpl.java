package com.underpressure.backend.services.google;

import java.util.Collections;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;

class FetchGoogleImpl implements FetchGoogle {

    JdbcTemplate jdbcTemplate;

    public FetchGoogleImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Payload userInfo(String idTokenString, String clientId) throws UserVerificationException {

        // * This method is not a part of integration tests.

        // https://developers.google.com/identity/gsi/web/guides/verify-google-id-token

        try {
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                return payload;
                // By specifying different scope in the frontend much more info can be fetched.
            } else
                throw new UserVerificationException();
        } catch (Exception e) {
            throw new UserVerificationException();
        }

    }

    public String sub(String idTokenString, String clientId)
            throws UserVerificationException {

        // * This method is mocked in integration tests.

        return this.userInfo(idTokenString, clientId).getSubject();
    }

    public Integer userId(String idTokenString, String clientId)
            throws UserVerificationException, UserDoesNotExistException {
        String googleSub = this.sub(idTokenString, clientId);

        String sql = "SELECT id FROM users WHERE google_sub=?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, googleSub);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserDoesNotExistException();
        }

    }

}
