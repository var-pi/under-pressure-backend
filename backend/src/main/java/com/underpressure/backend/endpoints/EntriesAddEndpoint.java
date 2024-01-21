package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.PostEndpoint;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Set;
import com.underpressure.backend.endpoints.helpers.ValidateProperty;

@RestController
public class EntriesAddEndpoint extends PostEndpoint {

    @Override
    @PostMapping("/personal/entries/add")
    public Map<String, Object> handle(@RequestBody Map<String, Object> requestData) {

        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");
        Integer stressLevel = (Integer) requestData.get("stressLevel");

        try {
            ValidateProperty.userId(userId, jdbcTemplate);
            ValidateProperty.subjectName(subjectName, jdbcTemplate);
            ValidateProperty.stressLevel(stressLevel);

            Set.entry(userId, subjectName, stressLevel, jdbcTemplate);

            return FeedbackMap.create(true, "The entry was successfully created/updated.");
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }

}
