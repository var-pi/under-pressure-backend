package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.GetEntriesRequestBody;
import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.helpers.FetchStatic;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class GetEntriesController extends PostController<List<EntryData>, GetEntriesRequestBody> {

    @Override
    @PostMapping("/personal/entries")
    public ResponseEntity<ApiResponse<List<EntryData>>> handle(@RequestBody GetEntriesRequestBody requestData) {

        try {
            Integer userId = requestData.getUserId();
            String subjectName = requestData.getSubjectName();

            Validate.userId(userId, jdbcTemplate);
            Validate.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = FetchStatic.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = FetchStatic.subjectInstanceId(userId, subjectId, jdbcTemplate);

            List<EntryData> entries = FetchStatic.entries(subjectInstanceId, jdbcTemplate);

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
