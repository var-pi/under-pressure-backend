package com.underpressure.backend.endpoints.helpers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ValidateProperty {

    public static void userId(String userId, JdbcTemplate jdbcTemplate) throws Exception {
        if (userId == null)
            throw new Exception("The passed data object didn't contain a userId property.");

        String sql = "SELECT id FROM users WHERE id='" + userId + "';";

        try {
            jdbcTemplate.queryForObject(sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("A user with given userId doesn't exists.");
        }
    }

    public static void subjectName(String subjectName, JdbcTemplate jdbcTemplate) throws Exception {

        if (subjectName == null)
            throw new Exception("The passed data object didn't contain a subjectName property.");

        String sql = "SELECT id FROM subjects WHERE name='" + subjectName + "';";

        try {
            jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("A subject with given subjectName doesn't exists.");
        }
    }

    public static void stressLevel(Integer stressLevel) throws Exception {
        if (stressLevel == null)
            throw new Exception("The passed data object didn't contain a stressLevel property.");

        if (stressLevel < 0 || stressLevel > 100)
            throw new Exception("The passed stressedLevel was outside the range of [0,100].");
    }

}
