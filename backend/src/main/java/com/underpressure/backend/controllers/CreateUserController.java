package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.CreateUserRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class CreateUserController extends PostController<String, CreateUserRequestBody> {

    @Override
    @PostMapping("/users/create")
    public ResponseEntity<ApiResponse<String>> handle(@RequestBody CreateUserRequestBody requestData) {
        try {
            String userId = requestData.getUserId();

            Validate.userId(userId, jdbcTemplate, false);
            Validate.userDoesNotExists(userId, jdbcTemplate);

            Add.user(userId, jdbcTemplate);

            return new ResponseEntity<>(
                    new ApiResponse<>(true, null, null),
                    HttpStatus.NO_CONTENT);

        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

}
