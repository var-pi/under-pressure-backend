package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;

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
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FollowSubjectRequestBody requestData) {

        validate.bearerToken(bearerToken);
        String idTokenString = extract.token(bearerToken);

        String subjectName = requestData.getSubjectName();
        validate.subjectName(subjectName);

        Integer userId = fetchGoogle.userId(idTokenString, clientId);

        Integer subjectId = fetchDB.subjectId(subjectName);
        if (check.subjectInstanceExists(userId, subjectId)) {
            Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId);

            validate.isUnfollowed(subjectInstanceId);

            set.toFollow(subjectInstanceId);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            add.subjectInstance(userId, subjectId);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

    }

}