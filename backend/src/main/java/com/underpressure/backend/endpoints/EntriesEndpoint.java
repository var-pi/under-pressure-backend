package com.underpressure.backend.endpoints;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.endpoints.PostEndpoint;
import com.underpressure.backend.endpoints.classes.request.data.EntryData;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.Parse;

@RestController
public class EntriesEndpoint extends PostEndpoint {

    @Override
    @PostMapping("/personal/entries")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {

        try {
            String userId = Parse.userId(requestData, jdbcTemplate);
            String subjectName = Parse.subjectName(requestData, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);
            Integer subjectInstanceId = Get.subjectInstanceId(userId, subjectId, jdbcTemplate);

            List<EntryData> entries = Get.entries(subjectInstanceId, jdbcTemplate);

            return FeedbackMap.create(true, "These are all the entries of this user for this subject", entries);
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage(), null);
        }
    }

}
