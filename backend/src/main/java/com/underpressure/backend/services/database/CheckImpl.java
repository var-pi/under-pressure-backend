package com.underpressure.backend.services.database;

import org.springframework.jdbc.core.JdbcTemplate;

class CheckImpl implements Check {

    JdbcTemplate jdbcTemplate;

    public CheckImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean subjectInstanceExists(Integer userId, String subjectUuid) {
        String sql = "SELECT COUNT(*) from subject_instances WHERE user_id=? AND subject_uuid=?";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId, subjectUuid);

        return rowCount > 0;
    }

    @Override
    public boolean subjectInstanceFollowed(Integer subjectInstanceId) {
        String sql = "SELECT COUNT(*) FROM subject_instances WHERE id=? AND if_followed=true";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);

        return rowCount > 0;
    }

    @Override
    public boolean entryExists(Integer subjectInstanceId) {
        String sql = "SELECT COUNT(*) FROM entries WHERE subject_instance_id=? AND creation_date=CURRENT_DATE";

        Integer rowCount = jdbcTemplate.queryForObject(sql, Integer.class, subjectInstanceId);

        return rowCount > 0;
    }

    @Override
    public boolean userExists(Integer userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE id=?";

        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return rowCount > 0;
    }

    @Override
    public boolean userWithGoogleSubExists(String googleSub) {
        String sql = "SELECT COUNT(*) FROM users WHERE google_sub=?";

        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, googleSub);

        return rowCount > 0;
    }

}
