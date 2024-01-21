package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.PostEndpoint;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.ValidateProperty;

@RestController
public class FollowedSubjectsEndpoint extends PostEndpoint {

    @Override
    @PostMapping("/personal/subjects")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {
        String userId = (String) requestData.get("userId");

        try {
            ValidateProperty.userId(userId, jdbcTemplate);

            return FeedbackMap.create(true, "These are the subjects that the user has chosen.",
                    Get.followedSubjects(userId, jdbcTemplate));
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage(), null);
        }
    }

}
