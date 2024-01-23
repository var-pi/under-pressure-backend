package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.NoRowsUpdatedUnexpectedException;

public class Update {

    public static void entry(Integer entryId, Integer stressLevel, JdbcTemplate jdbcTemplate)
            throws RequestException {

        String sql = "UPDATE entries SET stress_level=? WHERE id=?";

        int numOfRowsAffected = jdbcTemplate.update(sql, stressLevel, entryId);
        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();

    }

}
