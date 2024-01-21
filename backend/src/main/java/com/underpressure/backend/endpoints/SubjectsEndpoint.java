package com.underpressure.backend.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.endpoints.GetEndpoint;
import com.underpressure.backend.endpoints.helpers.FeedbackMap;
import com.underpressure.backend.endpoints.helpers.Get;

@RestController
public class SubjectsEndpoint extends GetEndpoint {

    @Override
    @GetMapping("/subjects")
    public Map<String, Object> handle() {
        try {
            return FeedbackMap.create(true, "These are all of the available subjects.", Get.subjects(jdbcTemplate));
        } catch (Exception e) {
            return FeedbackMap.create(false, e.getMessage(), null);
        }
    }

}
