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

@RestController
public class PersonalSubjectsEndpoint {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/subjects")
    @ResponseBody
    public Map<String, Object> dispatchPersonalSubjects(@RequestBody Map<String, Object> requestData) {

        String userId = (String) requestData.get("userId");

        Map<String, Object> res = new HashMap<>();

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

    @CrossOrigin(origins = "*")
    @PostMapping("/personal/subjects/add")
    public Map<String, Object> addSubject(@RequestBody Map<String, Object> requestData) {

        String userId = (String) requestData.get("userId");
        String subjectName = (String) requestData.get("subjectName");

        Map<String, Object> res = new HashMap<>();

        try {

            String requestForSubjectId = "SELECT id FROM subjects WHERE name='" + subjectName + "';";

            String subjectId = jdbcTemplate.queryForList(requestForSubjectId, String.class).get(0);

            String findSubjectInstance = "SELECT id from subject_instances WHERE user_id='" + userId
                    + "' AND subject_id='" + subjectId + "';";

            List<String> subjectInstances = jdbcTemplate.queryForList(findSubjectInstance, String.class);

            if (subjectInstances.size() > 0) {
                res.put("status", "fail");
                res.put("message", "This user already follows this subject. Creation of subject instance rejected.");

                return res;
            }

            String addSubjectInstance = "INSERT INTO subject_instances (user_id, subject_id) VALUES ('" + userId + "',"
                    + subjectId + ");";

            int numOfRowsAffected = jdbcTemplate.update(addSubjectInstance);

            if (numOfRowsAffected > 0) {
                res.put("status", "success");
                res.put("message", "The subject instance was successfully added.");

                return res;
            } else {
                res.put("status", "fail");
                res.put("message", "No expected flaw was detected but the subject instace wasn't created.");

                return res;
            }
        } catch (Exception e) {
            res.put("status", "fail");
            res.put("message", e.getMessage());

            return res;
        }

    }

}
