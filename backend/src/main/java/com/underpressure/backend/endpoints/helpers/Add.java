package com.underpressure.backend.endpoints.helpers;

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

    public static void entry(Integer subjectInstanceId, Integer stressLevel, JdbcTemplate jdbcTemplate)
            throws Exception {

        String sql = "INSERT INTO entries (subject_instance_id, created_at, stress_level) VALUES ("
                + subjectInstanceId
                + ", CURRENT_DATE, " + stressLevel + ")";

        int numOfRowsAffected = jdbcTemplate.update(sql);
        if (numOfRowsAffected == 0)
            throw new Exception("No expected exception was triggered but the entry was not created.");

    }
}
