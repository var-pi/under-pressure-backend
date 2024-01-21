package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.helpers.Add;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.If;
import com.underpressure.backend.endpoints.helpers.Set;
import com.underpressure.backend.endpoints.helpers.ValidateProperty;

@RestController
public class PersonalSubjectsAddEndpoint {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/subjects/follow")
    public Map<String, Object> followSubject(@RequestBody Map<String, Object> requestData) {
        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");

        try {
            ValidateProperty.userId(userId, jdbcTemplate);
            ValidateProperty.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);

            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            if (If.subjecetInstanceExists(userId, subjectId, jdbcTemplate)) {
                if (If.subjectInstanceFollowed(subjectInstanceId, jdbcTemplate))
                    throw new Exception(
                            "This user already follows this subject. Creation of subject instance rejected.");
                else {
                    Set.toFollow(subjectInstanceId, jdbcTemplate);
                    return FeedbackMap.create(true,
                            "The subject instance was successfully started to be followed again.");
                }
            }

            Add.subjectInstance(userId, subjectId, jdbcTemplate);
            return FeedbackMap.create(true, "The subject instance was successfully added.");

        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }
}