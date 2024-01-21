package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.endpoints.PostEndpoint;
import com.underpressure.backend.endpoints.helpers.Add;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.If;
import com.underpressure.backend.endpoints.helpers.Parse;
import com.underpressure.backend.endpoints.helpers.Set;
import com.underpressure.backend.endpoints.helpers.Validate;

@RestController
public class FollowSubjectEndpoint extends PostEndpoint {

    @Override
    @PostMapping("/personal/subjects/follow")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {

        try {
            String userId = Parse.userId(requestData, jdbcTemplate);
            String subjectName = Parse.subjectName(requestData, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);

            if (If.subjecetInstanceExists(userId, subjectId, jdbcTemplate)) {
                Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

                Validate.isUnfollowed(subjectInstanceId, jdbcTemplate);

                Set.toFollow(subjectInstanceId, jdbcTemplate);
                return FeedbackMap.create(true,
                        "The subject instance was successfully started to be followed again.");
            }

            Add.subjectInstance(userId, subjectId, jdbcTemplate);
            return FeedbackMap.create(true, "The subject instance was successfully added.");

        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }

}