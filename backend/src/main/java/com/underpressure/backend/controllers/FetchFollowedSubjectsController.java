package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.services.database.DatabaseService;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FetchFollowedSubjectsController
        extends AuthenticatedPostController<List<String>, FollowedSubjectsRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Extract extract;

    @Autowired
    DatabaseService databaseService;

    @Override
    @PostMapping("/personal/subjects")
    public ResponseEntity<List<String>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FollowedSubjectsRequestBody requestBody) throws RequestException {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = extract.token(bearerToken);

        Integer userId = fetchGoogle.userId(idTokenString, clientId);

        List<String> followedSubjects = fetchDB.followedSubjects(userId);
        return new ResponseEntity<>(followedSubjects, HttpStatus.OK);

    }

}
