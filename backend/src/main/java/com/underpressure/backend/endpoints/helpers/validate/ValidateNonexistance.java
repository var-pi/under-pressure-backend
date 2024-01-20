package com.underpressure.backend.endpoints.helpers.validate;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class ValidateNonexistance {
    public static void subjecet_instance(String userId, Integer subjectId, JdbcTemplate jdbcTemplate) throws Exception {
        String findSubjectInstance = "SELECT id from subject_instances WHERE user_id='" + userId
                + "' AND subject_id='" + subjectId + "';";

        List<String> subjectInstances = jdbcTemplate.queryForList(findSubjectInstance, String.class);

        if (subjectInstances.size() > 0)
            throw new Exception("This user already follows this subject. Creation of subject instance rejected.");
    }
}
