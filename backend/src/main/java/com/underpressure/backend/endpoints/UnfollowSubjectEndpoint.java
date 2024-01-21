package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.endpoints.PostEndpoint;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.Set;
import com.underpressure.backend.endpoints.helpers.Validate;

@RestController
public class UnfollowSubjectEndpoint extends PostEndpoint {

    @Override
    @PostMapping("/personal/subjects/unfollow")
    public Map<String, Object> handle(Map<String, Object> requestData) {

        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");

        try {
            Validate.userId(userId, jdbcTemplate);
            Validate.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);

            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            Validate.isFollowed(subjectInstanceId, jdbcTemplate);

            Set.toNotFollow(subjectInstanceId, jdbcTemplate);

            return FeedbackMap.create(true, "The subject was successfully unfollowed.");

        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }

}
