package com.underpressure.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.Parse;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FollowedSubjectsController extends PostController<List<String>> {

    @Override
    @PostMapping("/personal/subjects")
    public ResponseEntity<ApiResponse<List<String>>> handle(@RequestBody Map<String, Object> requestData) {

        try {
            String userId = Parse.userId(requestData, jdbcTemplate);

            List<String> followedSubjects = Get.followedSubjects(userId, jdbcTemplate);
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
