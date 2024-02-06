package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostController;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@RestController
public class FetchFollowedSubjectsController
        extends AuthenticatedPostController<List<String>, FollowedSubjectsRequestBody> {

    @Autowired
    UtilityService utilityService;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    GoogleService googleService;

    @Override
    @PostMapping("/personal/subjects")
    public ResponseEntity<List<String>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FollowedSubjectsRequestBody requestBody) throws RequestException {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        List<String> followedSubjects = databaseService.fetch().followedSubjects(userId);
        return new ResponseEntity<>(followedSubjects, HttpStatus.OK);

    }

}
