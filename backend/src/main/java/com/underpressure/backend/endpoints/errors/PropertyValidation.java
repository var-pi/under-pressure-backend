package com.underpressure.backend.endpoints.errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class PropertyValidation {

    public static Map<String, Object> userId(String userId, JdbcTemplate jdbcTemplate) {
        Map<String, Object> res = new HashMap<>();

        if (userId == null) {
            res.put("status", "fail");
            res.put("message", "The passed data object didn't contain a userId property.");

            return res;
        }

        try {
            String requestForUserId = "SELECT id FROM users WHERE id='" + userId + "';";

            List<String> userIds = jdbcTemplate.queryForList(requestForUserId, String.class);

            if (userIds.size() == 0) {
                res.put("status", "fail");
                res.put("message", "A user with such name was not found.");

                return res;
            }
        } catch (Exception e) {
            res.put("status", "fail");
            res.put("message", e.getMessage());

            return res;
        }

        return null;
    }

    public static Map<String, Object> subjectName(String subjectName, JdbcTemplate jdbcTemplate) {
        Map<String, Object> res = new HashMap<>();

        if (subjectName == null) {
            res.put("status", "fail");
            res.put("message", "The passed data object didn't contain a subjectName property.");

            return res;
        }

        try {
            String requestForSubjectIds = "SELECT id FROM subjects WHERE name='" + subjectName + "';";

            List<String> subjectIds = jdbcTemplate.queryForList(requestForSubjectIds, String.class);

            if (subjectIds.size() == 0) {
                res.put("status", "fail");
                res.put("message", "A subject with such name was not found.");

                return res;
            }
        } catch (Exception e) {
            res.put("status", "fail");
            res.put("message", e.getMessage());

            return res;
        }

        return null;
    }

}
