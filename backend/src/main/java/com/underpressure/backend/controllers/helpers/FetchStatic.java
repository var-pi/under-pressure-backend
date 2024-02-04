package com.underpressure.backend.controllers.helpers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload; // TODO Can I use a more genral one?
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.classes.request.data.EntryDataRowMapper;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.does_not_exist.TodaysEntryDoesNotExistException;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.SubjectInstanceDoesNotExistsException;

public class FetchStatic {

    public static List<String> subjects(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT name FROM subjects";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public static Integer subjectId(String subjectName, JdbcTemplate jdbcTemplate) throws RequestException {
        String requestForSubjectId = "SELECT id FROM subjects WHERE name=?";

        try {
            return jdbcTemplate.queryForObject(requestForSubjectId, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    public static List<String> followedSubjects(Integer userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id=? AND if_followed=true";

        return jdbcTemplate.queryForList(sql, String.class, userId);
    }

    public static Integer subjectInstanceId(Integer userId, Integer subjectId, JdbcTemplate jdbcTemplate)
            throws RequestException {
        String sql = "SELECT id FROM subject_instances WHERE user_id=? AND subject_id=?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectInstanceDoesNotExistsException();
        }

    }

    public static Integer todaysEntryId(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=? AND creation_date=CURRENT_DATE";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);
        } catch (EmptyResultDataAccessException e) {
            throw new TodaysEntryDoesNotExistException(sql);
        }
    }

    public static List<EntryData> entries(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM entries WHERE subject_instance_id=?";

        return jdbcTemplate.query(sql, new EntryDataRowMapper(), subjectInstanceId);
    }

    public static Payload userInfo(String idTokenString, String clientId) throws UserVerificationException {

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

    public static String googleSub(String idTokenString, String clientId)
            throws UserVerificationException {
        return FetchStatic.userInfo(idTokenString, clientId).getSubject();
    }

    public static Integer userId(String idTokenString, JdbcTemplate jdbcTemplate, String clientId)
            throws UserVerificationException {
        String googleSub = FetchStatic.googleSub(idTokenString, clientId);

        String sql = "SELECT id FROM users WHERE google_sub=?";

        return jdbcTemplate.queryForObject(sql, Integer.class, googleSub);
    }
}
