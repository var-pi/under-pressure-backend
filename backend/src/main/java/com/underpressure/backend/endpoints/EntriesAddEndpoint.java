package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.endpoints.PostEndpoint;
import com.underpressure.backend.endpoints.helpers.Add;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.If;
import com.underpressure.backend.endpoints.helpers.Update;
import com.underpressure.backend.endpoints.helpers.Validate;

@RestController
public class EntriesAddEndpoint extends PostEndpoint {

    @Override
    @PostMapping("/personal/entries/add")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {

        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");
        Integer stressLevel = (Integer) requestData.get("stressLevel");

        try {
            Validate.userId(userId, jdbcTemplate);
            Validate.subjectName(subjectName, jdbcTemplate);
            Validate.stressLevel(stressLevel);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            Validate.isFollowed(subjectInstanceId, jdbcTemplate);

            if (If.entryExists(subjectInstanceId, jdbcTemplate)) {
                Integer entryId = Get.entryId(subjectInstanceId, jdbcTemplate);

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
