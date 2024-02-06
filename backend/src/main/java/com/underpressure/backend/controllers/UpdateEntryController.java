package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.classes.request.body.AddEntryRequestBody;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@RestController
public class UpdateEntryController extends AuthenticatedPostController<String, AddEntryRequestBody> {

    @Autowired
    UtilityService utilityService;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    GoogleService googleService;

    @Override
    @PostMapping("/personal/entries/add")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody AddEntryRequestBody requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer stressLevel = requestData.getStressLevel();
        databaseService.validate().stressLevel(stressLevel);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        Integer subjectId = databaseService.fetch().subjectId(subjectName);
        Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectId);

        databaseService.validate().isFollowed(subjectInstanceId);

        if (databaseService.check().entryExists(subjectInstanceId)) {
            Integer entryId = databaseService.fetch().todaysEntryId(subjectInstanceId);

            databaseService.update().entry(entryId, stressLevel);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            databaseService.add().entry(subjectInstanceId, stressLevel);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

    }

}
