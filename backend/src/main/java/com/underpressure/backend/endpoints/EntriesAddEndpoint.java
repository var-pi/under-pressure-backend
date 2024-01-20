package com.underpressure.backend.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Set;
import com.underpressure.backend.endpoints.helpers.ValidateProperty;

@RestController
public class EntriesAddEndpoint {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/entries/add")
    @ResponseBody
    public Map<String, Object> dispatchEntries(@RequestBody Map<String, Object> requestData) {

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
