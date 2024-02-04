package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FollowedSubjectsController extends AuthenticatedPostController<List<String>, FollowedSubjectsRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Validate validate;

    @Autowired
    Extract extract;

    @Override
    @PostMapping("/personal/subjects")
    public ResponseEntity<ApiResponse<List<String>>> handle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody FollowedSubjectsRequestBody requestBody) {

        try {
            validate.bearerToken(bearerToken);
            String idTokenString = extract.token(bearerToken);

            Integer userId = fetchGoogle.userId(idTokenString, jdbcTemplate, clientId);

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
