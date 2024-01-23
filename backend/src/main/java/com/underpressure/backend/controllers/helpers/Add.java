package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.not_added.EntryNotAddedException;
import com.underpressure.backend.exceptions.unexpected.not_added.SubjectNotAddedException;
import com.underpressure.backend.exceptions.unexpected.not_added.UserNotAddedException;

public class Add {
        public static void subjectInstance(String userId, Integer subjectId, JdbcTemplate jdbcTemplate)
                        throws RequestException {

                String sql = "INSERT INTO subject_instances (user_id, subject_id, if_followed) VALUES (?,?,TRUE)";

                int numOfRowsAffected = jdbcTemplate.update(sql, userId, subjectId);

                if (numOfRowsAffected == 0)
                        throw new SubjectNotAddedException();
        }

        public static void entry(Integer subjectInstanceId, Integer stressLevel, JdbcTemplate jdbcTemplate)
                        throws RequestException {

                String sql = "INSERT INTO entries (subject_instance_id, created_at, stress_level) VALUES (?,CURRENT_DATE,?)";

                int numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId, stressLevel);

                if (numOfRowsAffected == 0)
                        throw new EntryNotAddedException();

        }

        public static void user(String userId, JdbcTemplate jdbcTemplate) throws RequestException {
                String sql = "INSERT INTO users(id) VALUES (?)";

                Integer rowsAffected = jdbcTemplate.update(sql, userId);

                if (rowsAffected == 0)
                        throw new UserNotAddedException();
        }
}
