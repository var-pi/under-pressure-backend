package com.underpressure.backend.controllers.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.not_added.EntryNotAddedException;
import com.underpressure.backend.exceptions.unexpected.not_added.SubjectNotAddedException;
import com.underpressure.backend.exceptions.unexpected.not_added.UserNotAddedException;

public class AddStatic {
        public static void subjectInstance(Integer userId, Integer subjectId, JdbcTemplate jdbcTemplate)
                        throws RequestException {

                String sql = "INSERT INTO subject_instances (user_id, subject_id, if_followed) VALUES (?,?,true)";

                int numOfRowsAffected = jdbcTemplate.update(sql, userId, subjectId);

                if (numOfRowsAffected == 0)
                        throw new SubjectNotAddedException();
        }

        public static void entry(Integer subjectInstanceId, Integer stressLevel, JdbcTemplate jdbcTemplate)
                        throws RequestException {

                String sql = "INSERT INTO entries (subject_instance_id, creation_date, stress_level) VALUES (?,CURRENT_DATE,?)";

                int numOfRowsAffected = jdbcTemplate.update(sql, subjectInstanceId, stressLevel);

                if (numOfRowsAffected == 0)
                        throw new EntryNotAddedException();

        }

        public static void user(Payload userInfo, JdbcTemplate jdbcTemplate) throws RequestException {
                String sql = "INSERT INTO users (google_sub, given_name) VALUES (?,?)";

                Integer rowsAffected = jdbcTemplate.update(sql, userInfo.getSubject(), userInfo.get("given_name"));

                if (rowsAffected == 0)
                        throw new UserNotAddedException();
        }
}
