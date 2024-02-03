package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.If;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FollowSubjectController extends PostController<String, FollowSubjectRequestBody> {

    @Override
    @PostMapping("/personal/subjects/follow")
    public ResponseEntity<ApiResponse<String>> handle(@RequestBody FollowSubjectRequestBody requestData) {

        try {
            Integer userId = requestData.getUserId();
            String subjectName = requestData.getSubjectName();

            Validate.userId(userId, jdbcTemplate);
            Validate.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);

            if (If.subjectInstanceExists(userId, subjectId, jdbcTemplate)) {
                Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

                Validate.isUnfollowed(subjectInstanceId, jdbcTemplate);

                Set.toFollow(subjectInstanceId, jdbcTemplate);

                return new ResponseEntity<>(
                        new ApiResponse<>(true, null, null),
                        HttpStatus.NO_CONTENT);
            } else {
                Add.subjectInstance(userId, subjectId, jdbcTemplate);

                return new ResponseEntity<>(
                        new ApiResponse<>(true, null, null),
                        HttpStatus.CREATED);
            }

        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

}