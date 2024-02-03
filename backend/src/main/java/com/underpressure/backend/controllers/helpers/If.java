package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

public class If {
    public static boolean subjectInstanceExists(Integer userId, Integer subjectId,
            JdbcTemplate jdbcTemplate) {
        String sql = "SELECT COUNT(*) from subject_instances WHERE user_id=? AND subject_id=?";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);

        return rowCount > 0;
    }

    public static boolean subjectInstanceFollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT COUNT(*) FROM subject_instances WHERE id=? AND if_followed=true";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);

        return rowCount > 0;
    }

    public static boolean entryExists(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT COUNT(*) FROM entries WHERE subject_instance_id=? AND creation_date=CURRENT_DATE";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);

        return rowCount > 0;
    }

    public static boolean userExists(Integer userId, JdbcTemplate jdbcTemplate) {
        String sql = "SELECT COUNT(*) FROM users WHERE id=?";

        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return rowCount > 0;
    }
}
