package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.UnfollowSubjectRequestBody;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.services.database.DatabaseService;
import com.underpressure.backend.controllers.services.google.GoogleService;

@RestController
public class UnfollowSubjectController extends AuthenticatedPostController<String, UnfollowSubjectRequestBody> {

    @Autowired
    Extract extract;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    GoogleService googleService;

    @Override
    @PostMapping("/personal/subjects/unfollow")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody UnfollowSubjectRequestBody requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = extract.token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        Integer subjectId = databaseService.fetch().subjectId(subjectName);
        Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectId);

        databaseService.validate().isFollowed(subjectInstanceId);

        databaseService.set().toNotFollow(subjectInstanceId);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

}
