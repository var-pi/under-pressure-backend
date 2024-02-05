package com.underpressure.backend.controllers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Check {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean subjectInstanceExists(Integer userId, Integer subjectId) {
        String sql = "SELECT COUNT(*) from subject_instances WHERE user_id=? AND subject_id=?";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectId);

        return rowCount > 0;
    }

    public boolean subjectInstanceFollowed(Integer subjectInstanceId) {
        String sql = "SELECT COUNT(*) FROM subject_instances WHERE id=? AND if_followed=true";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);

        return rowCount > 0;
    }

    public boolean entryExists(Integer subjectInstanceId) {
        String sql = "SELECT COUNT(*) FROM entries WHERE subject_instance_id=? AND creation_date=CURRENT_DATE";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);

        return rowCount > 0;
    }

    public boolean userExists(Integer userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE id=?";

        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return rowCount > 0;
    }

    public boolean userWithGoogleSubExists(String googleSub) {
        String sql = "SELECT COUNT(*) FROM users WHERE google_sub=?";

        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, googleSub);

        return rowCount > 0;
    }

}
