package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

public class Set {
    public static void toFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "UPDATE subject_instances SET if_followed=TRUE WHERE id=?";

        Integer numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId);

        if (numOfRowsAffected == 0)
            throw new Exception("No rows were updated.");
    }

    public static void toNotFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "UPDATE subject_instances SET if_followed=FALSE WHERE id=?";

        Integer numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId);

        if (numOfRowsAffected == 0)
            throw new Exception("No rows where affected.");
    }
}
