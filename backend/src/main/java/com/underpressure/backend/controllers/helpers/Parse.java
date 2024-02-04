package com.underpressure.backend.controllers.helpers;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.exceptions.RequestException;

public class Parse {

    public static Integer userId(Map<String, Object> requestData, JdbcTemplate jdbcTemplate, Boolean validate)
            throws RequestException {
        Integer userId = (int) requestData.get("userId");

        if (validate)
            ValidateStatic.userId(userId, jdbcTemplate);

        return userId;
    }

    public static Integer userId(Map<String, Object> requestData, JdbcTemplate jdbcTemplate) throws RequestException {
        return userId(requestData, jdbcTemplate, true);
    }

    public static String subjectName(Map<String, Object> requestData, JdbcTemplate jdbcTemplate)
            throws RequestException {
        String subjectName = (String) requestData.get("subjectName");

        ValidateStatic.subjectName(subjectName, jdbcTemplate);

        return subjectName;
    }

    public static Integer stressLevel(Map<String, Object> requestData, JdbcTemplate jdbcTemplate)
            throws RequestException {
        Integer stressLevel = (Integer) requestData.get("stressLevel");

        ValidateStatic.stressLevel(stressLevel);

        return stressLevel;
    }

}
