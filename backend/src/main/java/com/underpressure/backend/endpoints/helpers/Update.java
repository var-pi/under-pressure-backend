package com.underpressure.backend.endpoints.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

public class Update {

    public static void entry(Integer entryId, Integer stressLevel, JdbcTemplate jdbcTemplate)
            throws Exception {

        String sql = "UPDATE entries SET stress_level=" + stressLevel + " WHERE id=" + entryId + ";";

        int numOfRowsAffected = jdbcTemplate.update(sql);
        if (numOfRowsAffected == 0)
            throw new Exception("No expected exception was triggered but the entry was not updated.");

    }

}
