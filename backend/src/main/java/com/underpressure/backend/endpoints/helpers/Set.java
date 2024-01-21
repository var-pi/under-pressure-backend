package com.underpressure.backend.endpoints.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

import com.underpressure.backend.endpoints.exceptions.NotFoundException;

public class Set {
    public static void entry(String userId, String subjectName, Integer stressLevel, JdbcTemplate jdbcTemplate)
            throws Exception {
        Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);
        Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);
        if (!If.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
            throw new Exception("Subject instance exists but is not followed.");

        try {
            Integer entryId = Get.entryId(subjectInstanceId, jdbcTemplate);

            String sql = "UPDATE entries SET stress_level=" + stressLevel + " WHERE id=" + entryId + ";";

            int numOfRowsAffected = jdbcTemplate.update(sql);
            if (numOfRowsAffected == 0)
                throw new Exception("No expected exception was triggered but the entry was not updated.");
        } catch (NotFoundException e) {
            String sql = "INSERT INTO entries (subject_instance_id, created_at, stress_level) VALUES ("
                    + subjectInstanceId
                    + ", CURRENT_DATE, " + stressLevel + ")";

            int numOfRowsAffected = jdbcTemplate.update(sql);
            if (numOfRowsAffected == 0)
                throw new Exception("No expected exception was triggered but the entry was not created.");
        }
    }

    public static void toFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "UPDATE subject_instances SET if_followed=TRUE WHERE id=" + subjectInstanceId + ";";

        Integer numOfRowsAffected = jdbcTemplate.update(sql);

        if (numOfRowsAffected == 0)
            throw new Exception("No rows were updated.");
    }

    public static void toNotFollow(Integer subjectInstanceId, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = "UPDATE subject_instances SET if_followed=FALSE WHERE id=" + subjectInstanceId + ";";

        Integer numOfRowsAffected = jdbcTemplate.update(sql);

        if (numOfRowsAffected == 0)
            throw new Exception("No rows where affected.");
    }
}
