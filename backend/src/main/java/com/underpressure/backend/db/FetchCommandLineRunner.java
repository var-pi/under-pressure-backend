package com.underpressure.backend.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This component is meant to automize the process of updating the list subjects.
 */
//@Component
//@Order(1)
class FetchCommandLineRunner implements CommandLineRunner {
    @Getter
    @Setter
    private static class Body {
        private final String[] studyLevels = { "bachelor", "bachelor_master" };
        private final String[] structural_units = { "LTAT", "LTMS" };
        private final int take = 300;
        private int start = 1;
    }

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    public FetchCommandLineRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        ArrayList<Subject> subjects = new ArrayList<>();

        String url = "https://ois2.ut.ee/api/courses";

        Subject[] response;
        int start = 1;

        do {
            Body body = new Body();
            body.setStart(start);

            response = restTemplate.postForObject(url, body, Subject[].class);

            assert response != null;
            subjects.addAll(Arrays.asList(response));

            start += response.length;
        } while (response.length > 0);

        for (Subject subject : subjects) {
            String sql = "INSERT INTO subjects (name) VALUES (?)";

            jdbcTemplate.update(sql, subject.getTitle().getEt());
        }
    }

}
