package com.underpressure.backend.endpoints.helpers;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class Add {
    public static void subjectInstance(String userId, Integer subjectId, JdbcTemplate jdbcTemplate)
            throws Exception {

        String addSubjectInstance = "INSERT INTO subject_instances (user_id, subject_id, if_followed) VALUES ('"
                + userId + "',"
                + subjectId + ",TRUE);";

        int numOfRowsAffected = jdbcTemplate.update(addSubjectInstance);

        if (numOfRowsAffected == 0)
            throw new Exception("No expected flaw was detected but the subject instace wasn't created.");
    }
}
