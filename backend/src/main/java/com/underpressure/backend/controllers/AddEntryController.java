package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.classes.request.body.AddEntryRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.If;
import com.underpressure.backend.controllers.helpers.Update;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.RequestException;

@RestController
public class AddEntryController extends PostController<String, AddEntryRequestBody> {

    @Override
    @PostMapping("/personal/entries/add")
    public ResponseEntity<ApiResponse<String>> handle(@RequestBody AddEntryRequestBody requestData) {

        try {
            Integer userId = requestData.getUserId();
            String subjectName = requestData.getSubjectName();
            Integer stressLevel = requestData.getStressLevel();

            Validate.userId(userId, jdbcTemplate);
            Validate.subjectName(subjectName, jdbcTemplate);
            Validate.stressLevel(stressLevel);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            Validate.isFollowed(subjectInstanceId, jdbcTemplate);

            if (If.entryExists(subjectInstanceId, jdbcTemplate)) {
                Integer entryId = Get.todaysEntryId(subjectInstanceId, jdbcTemplate);

                Update.entry(entryId, stressLevel, jdbcTemplate);

                return new ResponseEntity<>(
                        new ApiResponse<>(true, null, null),
                        HttpStatus.NO_CONTENT);
            } else {
                Add.entry(subjectInstanceId, stressLevel, jdbcTemplate);

                return new ResponseEntity<>(
                        new ApiResponse<>(true, null, null),
                        HttpStatus.CREATED);
            }

        } catch (RequestException e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    e.getHttpStatus());
        }
    }

}
