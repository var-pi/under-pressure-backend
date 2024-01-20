package com.underpressure.backend.endpoints.personal;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.helpers.Add;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;
import com.underpressure.backend.endpoints.helpers.IfExists;
import com.underpressure.backend.endpoints.helpers.ValidateProperty;

@RestController
public class SubjectsAdd {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/subjects/add")
    public Map<String, Object> addSubject(@RequestBody Map<String, Object> requestData) {
        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");

        try {
            ValidateProperty.userId(userId, jdbcTemplate);
            ValidateProperty.subjectName(subjectName, jdbcTemplate);

            Integer subjectId = Get.subjectId(subjectName, jdbcTemplate);

            if (IfExists.subjecetInstance(userId, subjectId, jdbcTemplate))
                throw new Exception("This user already follows this subject. Creation of subject instance rejected.");

            return Add.subjectInstance(userId, subjectId, jdbcTemplate);
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage());
        }
    }
}