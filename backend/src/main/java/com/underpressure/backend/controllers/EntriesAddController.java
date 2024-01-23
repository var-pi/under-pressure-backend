package com.underpressure.backend.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.PostController;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.FeedbackMap;
import com.underpressure.backend.controllers.helpers.Get;
import com.underpressure.backend.controllers.helpers.If;
import com.underpressure.backend.controllers.helpers.Parse;
import com.underpressure.backend.controllers.helpers.Update;
import com.underpressure.backend.controllers.helpers.Validate;

@RestController
public class EntriesAddController extends PostController {

    @Override
    @PostMapping("/personal/entries/add")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {

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
                return FeedbackMap.create(true, "The entry was successfully updated.");
            } else {
                Add.entry(subjectInstanceId, stressLevel, jdbcTemplate);
                return FeedbackMap.create(true, "The entry was successfully created.");
            }

        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }

}
