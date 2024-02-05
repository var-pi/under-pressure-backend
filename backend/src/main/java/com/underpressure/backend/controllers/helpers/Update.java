package com.underpressure.backend.controllers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.NoRowsUpdatedUnexpectedException;

@Component
public class Update {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void entry(Integer entryId, Integer stressLevel)
            throws RequestException {

        String sql = "UPDATE entries SET stress_level=? WHERE id=?";

        int numOfRowsAffected = jdbcTemplate.update(sql, stressLevel, entryId);
        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();

    }

}
