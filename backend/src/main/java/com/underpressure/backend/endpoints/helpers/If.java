package com.underpressure.backend.endpoints.helpers;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class If {
    public static boolean subjecetInstanceExists(String userId, Integer subjectId,
            JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id from subject_instances WHERE user_id=? AND subject_id=?";

        List<String> subjectInstances = jdbcTemplate.queryForList(sql, String.class, userId, subjectId);

        return subjectInstances.size() != 0;
    }

    public static boolean subjectInstanceFollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id FROM subject_instances WHERE id=? AND if_followed=TRUE";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class, subjectInstanceId);

        return ids.size() != 0;
    }

    public static boolean entryExists(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id FROM entries WHERE subject_instance_id=? AND created_at=CURRENT_DATE";

        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class, subjectInstanceId);

        return ids.size() != 0;
    }

    public static boolean userExists(String userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT COUNT(*) FROM users WHERE id=?";

        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return rowCount > 0;
    }
}
