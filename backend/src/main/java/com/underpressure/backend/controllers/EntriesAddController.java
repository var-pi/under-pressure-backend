package com.underpressure.backend.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.If;
import com.underpressure.backend.controllers.helpers.Parse;
import com.underpressure.backend.controllers.helpers.Update;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class EntriesAddController extends PostController<String> {

    @Override
    @PostMapping("/personal/entries/add")
    public ResponseEntity<ApiResponse<String>> handle(@RequestBody Map<String, Object> requestData) {

        try {
            String userId = Parse.userId(requestData, jdbcTemplate);
            String subjectName = Parse.subjectName(requestData, jdbcTemplate);
            Integer stressLevel = Parse.stressLevel(requestData, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            Validate.isFollowed(subjectInstanceId, jdbcTemplate);

            if (If.entryExists(subjectInstanceId, jdbcTemplate)) {
                Integer entryId = Get.todaysEntryId(subjectInstanceId, jdbcTemplate);

                Update.entry(entryId, stressLevel, jdbcTemplate);
            } else {
                Add.entry(subjectInstanceId, stressLevel, jdbcTemplate);
            }

            return new ResponseEntity<>(
                    new ApiResponse<>(true, null, null),
                    HttpStatus.NO_CONTENT);

        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

}
