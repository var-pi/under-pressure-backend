package com.underpressure.backend.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.FeedbackMap;
import com.underpressure.backend.controllers.helpers.Parse;
import com.underpressure.backend.controllers.helpers.Validate;

@RestController
public class CreateUserController extends PostController {

    @Override
    @PostMapping("/users/create")
    public Map<String, Object> handle(Map<String, Object> requestData) {
        try {

            String userId = Parse.userId(requestData, jdbcTemplate, false);

            Validate.userDoesNotExists(userId, jdbcTemplate);

            Add.user(userId, jdbcTemplate);

            return FeedbackMap.create(true, "A new user was successfully created");
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }

}
