package com.underpressure.backend.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.helpers.FeedbackMap;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.Parse;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;

@RestController
public class UnfollowSubjectController extends PostController {

    @Override
    @PostMapping("/personal/subjects/unfollow")
    public Map<String, Object> handle(Map<String, Object> requestData) {

        try {
            String userId = Parse.userId(requestData, jdbcTemplate);
            String subjectName = Parse.subjectName(requestData, jdbcTemplate);

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
