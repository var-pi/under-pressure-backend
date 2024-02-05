package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.AuthenticatedPostController;
import com.underpressure.backend.controllers.classes.request.body.GetEntriesRequestBody;
import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class FetchEntriesController extends AuthenticatedPostController<List<EntryData>, GetEntriesRequestBody> {

    @Autowired
    Fetch.DB fetchDB;

    @Autowired
    Fetch.Google fetchGoogle;

    @Autowired
    Validate validate;

    @Autowired
    Extract extract;

    @Override
    @PostMapping("/personal/entries")
    public ResponseEntity<ApiResponse<List<EntryData>>> handle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody GetEntriesRequestBody requestData) {

        try {
            validate.bearerToken(bearerToken);
            String idTokenString = extract.token(bearerToken);

            String subjectName = requestData.getSubjectName();
            validate.subjectName(subjectName, jdbcTemplate);

            Integer userId = fetchGoogle.userId(idTokenString, jdbcTemplate, clientId);

            Integer subjectId = fetchDB.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = fetchDB.subjectInstanceId(userId, subjectId, jdbcTemplate);

            List<EntryData> entries = fetchDB.entries(subjectInstanceId, jdbcTemplate);

            return new ResponseEntity<>(
                    new ApiResponse<>(true, entries, null),
                    HttpStatus.OK);

        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

}
