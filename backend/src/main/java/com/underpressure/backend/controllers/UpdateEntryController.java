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
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Update;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Validate;

@RestController
public class UpdateEntryController extends AuthenticatedPostController<String, AddEntryRequestBody> {

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
    Update update;

    @Autowired
    Extract extract;

    @Override
    @PostMapping("/personal/entries/add")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody AddEntryRequestBody requestData) {

        validate.bearerToken(bearerToken);
        String idTokenString = extract.token(bearerToken);

        String subjectName = requestData.getSubjectName();
        validate.subjectName(subjectName);

        Integer stressLevel = requestData.getStressLevel();
        validate.stressLevel(stressLevel);

        Integer userId = fetchGoogle.userId(idTokenString, clientId);

        Integer subjectId = fetchDB.subjectId(subjectName);
        Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId);

        validate.isFollowed(subjectInstanceId);

        if (check.entryExists(subjectInstanceId)) {
            Integer entryId = fetchDB.todaysEntryId(subjectInstanceId);

            update.entry(entryId, stressLevel);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            add.entry(subjectInstanceId, stressLevel);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

    }

}
