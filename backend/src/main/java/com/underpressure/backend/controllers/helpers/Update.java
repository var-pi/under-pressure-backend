package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

public class Update {

    public static void entry(Integer entryId, Integer stressLevel, JdbcTemplate jdbcTemplate)
            throws Exception {

        String sql = "UPDATE entries SET stress_level=? WHERE id=?";

        int numOfRowsAffected = jdbcTemplate.update(sql, stressLevel, entryId);
        if (numOfRowsAffected == 0)
            throw new Exception("No expected exception was triggered but the entry was not updated.");

    }

}
