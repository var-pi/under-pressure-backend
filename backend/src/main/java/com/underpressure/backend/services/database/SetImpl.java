package com.underpressure.backend.services.database;

import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.NoRowsUpdatedUnexpectedException;

class SetImpl implements Set {

    JdbcTemplate jdbcTemplate;

    public SetImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void toFollow(Integer subjectInstanceId) throws RequestException {
        String sql = "UPDATE subject_instances SET if_followed=true WHERE id=?";

        Integer numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId);

        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();
    }

    @Override
    public void toNotFollow(Integer subjectInstanceId) throws RequestException {
        String sql = "UPDATE subject_instances SET if_followed=false WHERE id=?";

        Integer numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId);

        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();
    }
}
