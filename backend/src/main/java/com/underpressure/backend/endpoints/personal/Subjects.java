package com.underpressure.backend.endpoints.personal;

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

import com.underpressure.backend.endpoints.errors.PropertyValidation;

@RestController
public class Subjects {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/subjects")
    @ResponseBody
    public Map<String, Object> dispatchPersonalSubjects(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> res = new HashMap<>();

        String userId = (String) requestData.get("userId");

        Map<String, Object> answer = PropertyValidation.userId(userId, jdbcTemplate);
        if (answer != null)
            return answer;

        try {
            String sql = "SELECT subjects.name FROM subject_instances INNER JOIN subjects ON subject_instances.subject_id=subjects.id WHERE subject_instances.user_id='"
                    + userId + "'";

            List<PGobject> data = jdbcTemplate.queryForList(sql, PGobject.class);

            res.put("status", "success");
            res.put("message", "These are the subjects that the user has chosen.");
            res.put("data", data);

            return res;
        } catch (Exception e) {
            res.put("status", "fail");
            res.put("message", e.getMessage());
            res.put("data", null);

            return res;
        }
    }

}
