package com.underpressure.backend.endpoints;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;

@RestController
public class SubjectsEndpoint {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @GetMapping("/subjects")
    public Map<String, Object> dispatchSubjects() {

        try {
            return FeedbackMap.create(true, "These are all of the available subjects.", Get.subjects(jdbcTemplate));
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage(), null);
        }
    }

}
