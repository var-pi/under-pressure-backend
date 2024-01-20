package com.underpressure.backend.endpoints.helpers;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class If {
    public static boolean subjecetInstanceExists(String userId, Integer subjectId,
            JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id from subject_instances WHERE user_id='" + userId
                + "' AND subject_id='" + subjectId + "';";

        List<String> subjectInstances = jdbcTemplate.queryForList(sql, String.class);

        return subjectInstances.size() != 0;
    }

    public static boolean subjectInstanceFollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id FROM subject_instances WHERE id=" + subjectInstanceId + " AND if_followed=TRUE;";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class);

        return ids.size() != 0;
    }

    public static boolean entryExists(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=" + subjectInstanceId
                + " AND created_at=CURRENT_DATE;";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class);

        return ids.size() != 0;
    }
}
