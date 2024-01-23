package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostControllerNew;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FollowedSubjectsController extends PostControllerNew<List<String>, FollowedSubjectsRequestBody> {

    @Override
    @PostMapping("/personal/subjects")
    public ResponseEntity<ApiResponse<List<String>>> handle(@RequestBody FollowedSubjectsRequestBody r) {

        try {
            Validate.userId(r.getUserId(), jdbcTemplate);

            List<String> followedSubjects = Get.followedSubjects(r.getUserId(), jdbcTemplate);
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
