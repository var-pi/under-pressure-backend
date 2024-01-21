package com.underpressure.backend.endpoints.helpers;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class Parse {

    public static String userId(Map<String, Object> requestData, JdbcTemplate jdbcTemplate, Boolean validate)
            throws Exception {
        String userId = (String) requestData.get("userId");

        if (validate)
            Validate.userId(userId, jdbcTemplate);

        return userId;
    }

    public static String userId(Map<String, Object> requestData, JdbcTemplate jdbcTemplate) throws Exception {
        return userId(requestData, jdbcTemplate, true);
    }

    public static String subjectName(Map<String, Object> requestData, JdbcTemplate jdbcTemplate) throws Exception {
        String subjectName = (String) requestData.get("subjectName");

        Validate.subjectName(subjectName, jdbcTemplate);

        return subjectName;
    }

    public static Integer stressLevel(Map<String, Object> requestData, JdbcTemplate jdbcTemplate) throws Exception {
        Integer stressLevel = (Integer) requestData.get("stressLevel");

        Validate.stressLevel(stressLevel);

        return stressLevel;
    }

}
