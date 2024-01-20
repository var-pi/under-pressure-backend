package com.underpressure.backend.endpoints.helpers.validate;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class ValidateProperty {

    public static void userId(String userId, JdbcTemplate jdbcTemplate) throws Exception {
        if (userId == null)
            throw new Exception("The passed data object didn't contain a userId property.");

        String requestForUserId = "SELECT id FROM users WHERE id='" + userId + "';";

        List<String> userIds = jdbcTemplate.queryForList(requestForUserId, String.class);

        if (userIds.size() == 0)
            throw new Exception("A user with such name was not found.");
    }

    public static void subjectName(String subjectName, JdbcTemplate jdbcTemplate) throws Exception {

        if (subjectName == null)
            throw new Exception("The passed data object didn't contain a subjectName property.");

        String requestForSubjectIds = "SELECT id FROM subjects WHERE name='" + subjectName + "';";

        List<String> subjectIds = jdbcTemplate.queryForList(requestForSubjectIds, String.class);

        if (subjectIds.size() == 0)
            throw new Exception("A subject with such name was not found.");
    }

    public static void stressLevel(Integer stressLevel) throws Exception {
        if (stressLevel == null)
            throw new Exception("The passed data object didn't contain a stressLevel property.");

        if (stressLevel < 0 || stressLevel > 100)
            throw new Exception("The passed stressedLevel was outside the range of [0,100].");
    }

}
