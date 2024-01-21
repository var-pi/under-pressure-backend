package com.underpressure.backend.endpoints.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

public class Set {
    public static void toFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "UPDATE subject_instances SET if_followed=TRUE WHERE id=" + subjectInstanceId + ";";

        Integer numOfRowsAffected = jdbcTemplate.update(sql);

        if (numOfRowsAffected == 0)
            throw new Exception("No rows were updated.");
    }

    public static void toNotFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "UPDATE subject_instances SET if_followed=FALSE WHERE id=" + subjectInstanceId + ";";

        Integer numOfRowsAffected = jdbcTemplate.update(sql);

        if (numOfRowsAffected == 0)
            throw new Exception("No rows where affected.");
    }
}
