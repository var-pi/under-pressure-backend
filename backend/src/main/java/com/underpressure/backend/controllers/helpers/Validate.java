package com.underpressure.backend.controllers.helpers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.StressLevelParameterException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.exceptions.parameter.UserIdParameterException;

public class Validate {

    public static void userId(String userId, JdbcTemplate jdbcTemplate) throws Exception {
        if (userId == null)
            throw new UserIdParameterException();

        String sql = "SELECT id FROM users WHERE id=?";

        try {
            jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExistException();
        }
    }

    public static void subjectName(String subjectName, JdbcTemplate jdbcTemplate) throws Exception {

        if (subjectName == null)
            throw new SubjectNameParameterException();

        String sql = "SELECT id FROM subjects WHERE name=?";

        try {
            jdbcTemplate.queryForObject(sql, Integer.class, subjectName);
        } catch (EmptyResultDataAccessException e) {
            throw new SubjectDoesNotExist();
        }
    }

    public static void stressLevel(Integer stressLevel) throws Exception {
        if (stressLevel == null)
            throw new StressLevelParameterException();

        if (stressLevel < 0 || stressLevel > 100)
            throw new Exception("The passed stressedLevel was outside the range of [0,100].");
    }

    public static void isFollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        if (!If.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new Exception("This subject is unfollowed.");
    }

    public static void isUnfollowed(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        if (If.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new Exception("This subject is followed.");
    }

    public static void userDoesNotExists(String userId, JdbcTemplate jdbcTemplate) throws Exception {
        if (If.userExists(userId, jdbcTemplate))
            throw new Exception("User with this userId exists.");
    }
}
