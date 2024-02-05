package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FollowSubjectController extends AuthenticatedPostController<String, FollowSubjectRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Add add;

    @Autowired
    Check check;

    @Autowired
    Validate validate;

    @Autowired
    Set set;

    @Autowired
    Extract extract;

    @Override
    @PostMapping("/personal/subjects/follow")
    public ResponseEntity<ApiResponse<String>> handle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody FollowSubjectRequestBody requestData) {
        try {
            String idTokenString = extract.token(bearerToken);
            validate.bearerToken(idTokenString);

            String subjectName = requestData.getSubjectName();
            validate.subjectName(subjectName, jdbcTemplate);

            Integer userId = fetchGoogle.userId(idTokenString, jdbcTemplate, clientId);

            Integer subjectId = fetchDB.subjectId(subjectName, jdbcTemplate);
            if (check.subjectInstanceExists(userId, subjectId, jdbcTemplate)) {
                Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId,
                        jdbcTemplate);

                validate.isUnfollowed(subjectInstanceId, jdbcTemplate);

                set.toFollow(subjectInstanceId, jdbcTemplate);

                return new ResponseEntity<>(
                        new ApiResponse<>(true, null, null),
                        HttpStatus.NO_CONTENT);
            } else {
                add.subjectInstance(userId, subjectId, jdbcTemplate);

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