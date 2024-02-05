package com.underpressure.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.AddEntryRequestBody;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.services.database.DatabaseService;

@RestController
public class UpdateEntryController extends AuthenticatedPostController<String, AddEntryRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Extract extract;

    @Autowired
    DatabaseService databaseService;

    @Override
    @PostMapping("/personal/entries/add")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody AddEntryRequestBody requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = extract.token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer stressLevel = requestData.getStressLevel();
        databaseService.validate().stressLevel(stressLevel);

        Integer userId = fetchGoogle.userId(idTokenString, clientId);

        Integer subjectId = fetchDB.subjectId(subjectName);
        Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId);

        databaseService.validate().isFollowed(subjectInstanceId);

        if (databaseService.check().entryExists(subjectInstanceId)) {
            Integer entryId = fetchDB.todaysEntryId(subjectInstanceId);

            databaseService.update().entry(entryId, stressLevel);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            databaseService.add().entry(subjectInstanceId, stressLevel);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

    }

}
