package com.underpressure.backend.services.database;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.already_exists.SubjectAlreadyFollowedException;
import com.underpressure.backend.exceptions.already_exists.SubjectUnfollowedException;
import com.underpressure.backend.exceptions.already_exists.UserAlreadyExistsException;
import com.underpressure.backend.exceptions.auth.BearerTokenNullException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.CodeParameterException;
import com.underpressure.backend.exceptions.parameter.StressLevelParameterException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.exceptions.parameter.UserIdParameterException;
import com.underpressure.backend.exceptions.range.StressLevelRangeException;

@Component
class ValidateImpl implements Validate {

    JdbcTemplate jdbcTemplate;
    DatabaseService databaseService;

    public ValidateImpl(DatabaseService databaseService, JdbcTemplate jdbcTemplate) {
        this.databaseService = databaseService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void userId(Integer userId, boolean hasToExist) throws RequestException {

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

    @Override
    public void userId(Integer userId) throws RequestException {

        // TODO Is this method needed?

        userId(userId, true);
    }

    @Override
    public void subjectName(String subjectName) throws RequestException {

        if (subjectName == null)
            throw new SubjectNameParameterException();

        String sql = "SELECT id FROM subjects WHERE name=?";

        try {
            jdbcTemplate.queryForObject(sql, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    @Override
    public void stressLevel(Integer stressLevel) throws RequestException {
        if (stressLevel == null)
            throw new StressLevelParameterException();

        if (stressLevel < 0 || stressLevel > 100)
            throw new StressLevelRangeException();
    }

    @Override
    public void isFollowed(Integer subjectInstanceId) throws RequestException {
        if (!databaseService.check().subjectInstanceFollowed(subjectInstanceId))
            throw new SubjectUnfollowedException();
    }

    @Override
    public void isUnfollowed(Integer subjectInstanceId) throws RequestException {
        if (databaseService.check().subjectInstanceFollowed(subjectInstanceId))
            throw new SubjectAlreadyFollowedException();
    }

    @Override
    public void userDoesNotExists(Integer userId) throws RequestException {
        if (databaseService.check().userExists(userId))
            throw new UserAlreadyExistsException();
    }

    @Override
    public void code(String code) throws RequestException {
        if (code == null)
            throw new CodeParameterException();
    }

    @Override
    public void bearerToken(String bearerToken) throws RequestException {
        if (bearerToken == null)
            throw new BearerTokenNullException();
    }

}
