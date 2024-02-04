package com.underpressure.backend.controllers.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.already_exists.SubjectAlreadyFollowedException;
import com.underpressure.backend.exceptions.already_exists.SubjectAlreadyUnfollowedException;
import com.underpressure.backend.exceptions.already_exists.UserAlreadyExistsException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.CodeParameterException;
import com.underpressure.backend.exceptions.parameter.IdTokenStringParameterException;
import com.underpressure.backend.exceptions.parameter.StressLevelParameterException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.exceptions.parameter.UserIdParameterException;
import com.underpressure.backend.exceptions.range.StressLevelRangeException;

public class ValidateStatic {

    // TODO convert from using static to using a bean.
    // @Autowired
    // Check check;

    public static void userId(Integer userId, JdbcTemplate jdbcTemplate, boolean hasToExist) throws RequestException {
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

    public static void userId(Integer userId, JdbcTemplate jdbcTemplate) throws RequestException {
        userId(userId, jdbcTemplate, true);
    }

    public static void subjectName(String subjectName, JdbcTemplate jdbcTemplate) throws RequestException {

        if (subjectName == null)
            throw new SubjectNameParameterException();

        String sql = "SELECT id FROM subjects WHERE name=?";

        try {
            jdbcTemplate.queryForObject(sql, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    public static void stressLevel(Integer stressLevel) throws RequestException {
        if (stressLevel == null)
            throw new StressLevelParameterException();

        if (stressLevel < 0 || stressLevel > 100)
            throw new StressLevelRangeException();
    }

    public static void isFollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        if (!IfStatic.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new SubjectAlreadyUnfollowedException();
    }

    public static void isUnfollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws RequestException {
        if (IfStatic.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new SubjectAlreadyFollowedException();
    }

    public static void userDoesNotExists(Integer userId, JdbcTemplate jdbcTemplate) throws RequestException {
        if (IfStatic.userExists(userId, jdbcTemplate))
            throw new UserAlreadyExistsException();
    }

    public static void code(String code) throws RequestException {
        if (code == null)
            throw new CodeParameterException();
    }

    public static void idTokenString(String idTokenString) throws RequestException {
        if (idTokenString == null)
            throw new IdTokenStringParameterException();
    }
}
