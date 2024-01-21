package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.endpoints.PostEndpoint;
import com.underpressure.backend.endpoints.helpers.Add;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Parse;
import com.underpressure.backend.endpoints.helpers.Validate;

@RestController
public class CreateUserEndpoint extends PostEndpoint {

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
