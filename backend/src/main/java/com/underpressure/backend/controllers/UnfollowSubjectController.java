package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.UnfollowSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class UnfollowSubjectController extends PostController<String, UnfollowSubjectsRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Validate validate;

    @Override
    @PostMapping("/personal/subjects/unfollow")
    public ResponseEntity<ApiResponse<String>> handle(UnfollowSubjectsRequestBody requestData) {

        try {
            Integer userId = requestData.getUserId();
            String subjectName = requestData.getSubjectName();

            validate.userId(userId, jdbcTemplate);
            validate.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = fetchDB.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId, jdbcTemplate);

            validate.isFollowed(subjectInstanceId, jdbcTemplate);

            Set.toNotFollow(subjectInstanceId, jdbcTemplate);

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
