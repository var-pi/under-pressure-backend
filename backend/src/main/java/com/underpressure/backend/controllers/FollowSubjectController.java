package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@RestController
public class FollowSubjectController extends AuthenticatedPostController<String, FollowSubjectRequestBody> {

    @Autowired
    UtilityService utilityService;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    GoogleService googleService;

    @Override
    @PostMapping("/personal/subjects/follow")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FollowSubjectRequestBody requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        Integer subjectId = databaseService.fetch().subjectId(subjectName);
        if (databaseService.check().subjectInstanceExists(userId, subjectId)) {
            Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectId);

            databaseService.validate().isUnfollowed(subjectInstanceId);

            databaseService.set().toFollow(subjectInstanceId);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            databaseService.add().subjectInstance(userId, subjectId);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

    }

}