package com.underpressure.backend.endpoints.helpers;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class IfExists {
    public static boolean subjecetInstance(String userId, Integer subjectId,
            JdbcTemplate jdbcTemplate) {
        String findSubjectInstance = "SELECT id from subject_instances WHERE user_id='" + userId
                + "' AND subject_id='" + subjectId + "';";

        List<String> subjectInstances = jdbcTemplate.queryForList(findSubjectInstance, String.class);

        return subjectInstances.size() != 0;
    }

    public static boolean entry(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=" + subjectInstanceId
                + " AND created_at=CURRENT_DATE;";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class);

        return ids.size() != 0;
    }
}
