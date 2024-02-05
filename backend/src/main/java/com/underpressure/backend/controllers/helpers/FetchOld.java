package com.underpressure.backend.controllers.helpers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.classes.request.data.EntryDataRowMapper;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.SubjectInstanceDoesNotExistsException;
import com.underpressure.backend.exceptions.does_not_exist.TodaysEntryDoesNotExistException;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;

@Component
public class FetchOld {

    // @Component
    // public static class DB {

    // @Autowired
    // JdbcTemplate jdbcTemplate;

    // public List<String> subjects() {
    // String sql = "SELECT name FROM subjects";

    // return jdbcTemplate.queryForList(sql, String.class);
    // }

    // public Integer subjectId(String subjectName) throws RequestException {
    // String requestForSubjectId = "SELECT id FROM subjects WHERE name=?";

    // try {
    // return jdbcTemplate.queryForObject(requestForSubjectId, Integer.class,
    // subjectName);
    // } catch (EmptyResultDataAccessException e) {
    // throw new SubjectDoesNotExist();
    // }
    // }

    // public List<String> followedSubjects(Integer userId) {
    // String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects
    // ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id=?
    // AND if_followed=true";

    // return jdbcTemplate.queryForList(sql, String.class, userId);
    // }

    // public Integer subjectInstanceId(Integer userId, Integer subjectId)
    // throws RequestException {
    // String sql = "SELECT id FROM subject_instances WHERE user_id=? AND
    // subject_id=?";

    // try {
    // return jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);
    // } catch (EmptyResultDataAccessException e) {
    // throw new SubjectInstanceDoesNotExistsException();
    // }

    // }

    // public Integer todaysEntryId(Integer subjectInstanceId) throws
    // RequestException {
    // String sql = "SELECT id FROM entries WHERE subject_instance_id=? AND
    // creation_date=CURRENT_DATE";

    // try {
    // return jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);
    // } catch (EmptyResultDataAccessException e) {
    // throw new TodaysEntryDoesNotExistException(sql);
    // }
    // }

    // public List<EntryData> entries(Integer subjectInstanceId) {
    // String sql = "SELECT * FROM entries WHERE subject_instance_id=?";

    // return jdbcTemplate.query(sql, new EntryDataRowMapper(), subjectInstanceId);
    // }
    // }

    @Component
    public static class Google {

        @Autowired
        JdbcTemplate jdbcTemplate;

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

}