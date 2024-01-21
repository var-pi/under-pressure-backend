package com.underpressure.backend.endpoints.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

public class Add {
        public static void subjectInstance(String userId, Integer subjectId, JdbcTemplate jdbcTemplate)
                        throws Exception {

                String sql = "INSERT INTO subject_instances (user_id, subject_id, if_followed) VALUES (?,?,TRUE)";

                int numOfRowsAffected = jdbcTemplate.update(sql, userId, subjectId);

                if (numOfRowsAffected == 0)
                        throw new Exception("No expected flaw was detected but the subject instace wasn't created.");
        }

        public static void entry(Integer subjectInstanceId, Integer stressLevel, JdbcTemplate jdbcTemplate)
                        throws Exception {

                String sql = "INSERT INTO entries (subject_instance_id, created_at, stress_level) VALUES (?,CURRENT_DATE,?)";

                int numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId, stressLevel);

                if (numOfRowsAffected == 0)
                        throw new Exception("No expected exception was triggered but the entry was not created.");

        }

        public static void user(String userId, JdbcTemplate jdbcTemplate) throws Exception {
                String sql = "INSERT INTO users(id) VALUES (?)";

                Integer rowsAffected = jdbcTemplate.update(sql, userId);

                if (rowsAffected == 0)
                        throw new Exception("No expected exception was triggered but a new user was not created.");
        }
}
