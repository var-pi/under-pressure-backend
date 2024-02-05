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
import com.underpressure.backend.controllers.classes.request.body.GetEntriesRequestBody;
import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.services.database.DatabaseService;

@RestController
public class FetchEntriesController extends AuthenticatedPostController<List<EntryData>, GetEntriesRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Extract extract;

    @Autowired
    DatabaseService databaseService;

    @Override
    @PostMapping("/personal/entries")
    public ResponseEntity<List<EntryData>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody GetEntriesRequestBody requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = extract.token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = fetchGoogle.userId(idTokenString, clientId);

        Integer subjectId = fetchDB.subjectId(subjectName);
        Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId);

        List<EntryData> entries = fetchDB.entries(subjectInstanceId);

        return new ResponseEntity<>(entries, HttpStatus.OK);

    }

}
