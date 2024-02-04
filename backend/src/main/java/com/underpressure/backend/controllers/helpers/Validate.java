package com.underpressure.backend.controllers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.already_exists.SubjectAlreadyFollowedException;
import com.underpressure.backend.exceptions.already_exists.SubjectAlreadyUnfollowedException;
import com.underpressure.backend.exceptions.already_exists.UserAlreadyExistsException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.CodeParameterException;
import com.underpressure.backend.exceptions.parameter.BearerTokenParameterException;
import com.underpressure.backend.exceptions.parameter.StressLevelParameterException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.exceptions.parameter.UserIdParameterException;
import com.underpressure.backend.exceptions.range.StressLevelRangeException;

@Component
public class Validate {

    @Autowired
    Check check;

    public void userId(Integer userId, JdbcTemplate jdbcTemplate, boolean hasToExist) throws RequestException {

        // TODO Is this method needed?

        if (userId == null)
            throw new UserIdParameterException();

        String sql = "SELECT id FROM users WHERE id=?";

        if (hasToExist)
            try {
                jdbcTemplate.queryForObject(sql, String.class, userId);
            } catch (EmptyResultDataAccessException e) {
                throw new UserDoesNotExistException();
            }
    }

    public void userId(Integer userId, JdbcTemplate jdbcTemplate) throws RequestException {

        // TODO Is this method needed?

        userId(userId, jdbcTemplate, true);
    }

    public void subjectName(String subjectName, JdbcTemplate jdbcTemplate) throws RequestException {

        if (subjectName == null)
            throw new SubjectNameParameterException();

        String sql = "SELECT id FROM subjects WHERE name=?";

        try {
            jdbcTemplate.queryForObject(sql, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    public void stressLevel(Integer stressLevel) throws RequestException {
        if (stressLevel == null)
            throw new StressLevelParameterException();

        if (stressLevel < 0 || stressLevel > 100)
            throw new StressLevelRangeException();
    }

    public void isFollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        if (!check.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new SubjectAlreadyUnfollowedException();
    }

    public void isUnfollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        if (check.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new SubjectAlreadyFollowedException();
    }

    public void userDoesNotExists(Integer userId, JdbcTemplate jdbcTemplate) throws RequestException {
        if (check.userExists(userId, jdbcTemplate))
            throw new UserAlreadyExistsException();
    }

    public void code(String code) throws RequestException {
        if (code == null)
            throw new CodeParameterException();
    }

    public void bearerToken(String bearerToken) throws RequestException {
        if (bearerToken == null)
            throw new BearerTokenParameterException();
    }

}
