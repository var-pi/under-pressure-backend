package com.underpressure.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.helpers.FeedbackMap;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.Parse;

@RestController
public class FollowedSubjectsController extends PostController {

    @Override
    @PostMapping("/personal/subjects")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {

        try {
            String userId = Parse.userId(requestData, jdbcTemplate);

            List<String> followedSubjects = Get.followedSubjects(userId, jdbcTemplate);
            return FeedbackMap.create(true, "These are the subjects that the user has chosen.", followedSubjects);
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage(), null);
        }
    }

}
