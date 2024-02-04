package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.controllers.exceptions.NotFoundException;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.If;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.exceptions.unexpected.not_added.UserNotAddedException;

@RestController
public class FollowSubjectController extends PostController<String, FollowSubjectRequestBody> {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Fetch.DB fetchDB;

    @Override
    @PostMapping("/personal/subjects/follow")
    public ResponseEntity<ApiResponse<String>> handle(@RequestBody FollowSubjectRequestBody requestData) {

        try {
            String idTokenString = requestData.getIdTokenString();
            String subjectName = requestData.getSubjectName();

            Validate.idTokenString(idTokenString);
            Validate.subjectName(subjectName, jdbcTemplate);

            Integer userId = fetchGoogle.userId(idTokenString, jdbcTemplate, clientId);
            Validate.userId(userId, jdbcTemplate);

            Integer subjectId = fetchDB.subjectId(subjectName, jdbcTemplate);
            throw new Exception(Integer.toString(subjectId));
            // if (If.subjectInstanceExists(userId, subjectId, jdbcTemplate)) {
            // Integer subjectInstanceId = fetchGoogle.subjectInstanceId(userId, subjectId,
            // jdbcTemplate);

            // Validate.isUnfollowed(subjectInstanceId, jdbcTemplate);

            // Set.toFollow(subjectInstanceId, jdbcTemplate);

            // return new ResponseEntity<>(
            // new ApiResponse<>(true, null, null),
            // HttpStatus.NO_CONTENT);
            // } else {
            // Add.subjectInstance(userId, subjectId, jdbcTemplate);

            // return new ResponseEntity<>(
            // new ApiResponse<>(true, null, null),
            // HttpStatus.CREATED);
            // }

        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}