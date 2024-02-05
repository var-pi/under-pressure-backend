package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.NoRowsUpdatedUnexpectedException;

@Component
public class Set {
    public void toFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        String sql = "UPDATE subject_instances SET if_followed=true WHERE id=?";

        Integer numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId);

        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();
    }

    public void toNotFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        String sql = "UPDATE subject_instances SET if_followed=false WHERE id=?";

        Integer numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId);

        if (numOfRowsAffected == 0)
            throw new NoRowsUpdatedUnexpectedException();
    }
}
