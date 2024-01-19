package com.underpressure.backend.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectsEndpoint {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @GetMapping("/subjects")
    public Map<String, Object> dispatchSubjects() {

        Map<String, Object> res = new HashMap<>();

        try {
            String sql = "SELECT name FROM subjects;";

            List<String> data = jdbcTemplate.queryForList(sql, String.class);

            System.out.println(data);

            res.put("status", "success");
            res.put("message", "These are all of the available subjects.");
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
