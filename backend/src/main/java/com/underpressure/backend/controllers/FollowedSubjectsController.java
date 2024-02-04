package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FollowedSubjectsController extends PostController<List<String>, FollowedSubjectsRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Validate validate;

    @Override
    @PostMapping("/personal/subjects")
    public ResponseEntity<ApiResponse<List<String>>> handle(@RequestBody FollowedSubjectsRequestBody requestBody) {

        try {
            Integer userId = requestBody.getUserId();

            validate.userId(userId, jdbcTemplate);

            List<String> followedSubjects = fetchDB.followedSubjects(userId, jdbcTemplate);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, followedSubjects, null),
                    HttpStatus.OK);
        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

}
