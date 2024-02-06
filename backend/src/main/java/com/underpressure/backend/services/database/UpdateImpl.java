package com.underpressure.backend.services.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.NoRowsUpdatedUnexpectedException;

@Component
class UpdateImpl implements Update {

    JdbcTemplate jdbcTemplate;

    public UpdateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void entry(Integer entryId, Integer stressLevel)
            throws RequestException {

        String sql = "UPDATE entries SET stress_level=? WHERE id=?";

        int numOfRowsAffected = jdbcTemplate.update(sql, stressLevel, entryId);
        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();

    }

}
