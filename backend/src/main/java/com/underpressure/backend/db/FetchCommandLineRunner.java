package com.underpressure.backend.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This component is meant to automize the process of updating the list subjects.
 */
//@Component
@Order(1)
class FetchCommandLineRunner implements CommandLineRunner {
    @Getter
    @Setter
    private static class Body {
        private final String[] studyLevels = { "bachelor", "bachelor_master" };
        private final String[] structural_units = { "LTAT", "LTMS" };
        private final int take = 300;
        private int start;

        public Body(int start) {
            this.start = start;
        }
    }

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    public FetchCommandLineRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        String url = "https://ois2.ut.ee/api/courses";

        Subject[] subjects;
        int start = 1;

        // Create a list to hold batch parameters
        List<Object[]> batchParameters = new ArrayList<>();

        do {
            Body body = new Body(start);

            subjects = restTemplate.postForObject(url, body, Subject[].class);

            assert subjects != null;

            // Prepare batch parameters
            for (Subject subject : subjects) {
                String subjectFormatted = String.format(
                        "%s %s (%d EAP)",
                        subject.getCode(),
                        subject.getTitle().getEt(),
                        subject.getCredits());

                // Add parameters to the batch
                batchParameters.add(new Object[]{subject.getUuid(), subjectFormatted});
            }

            start += subjects.length;
        } while (subjects.length > 0);

        // SQL statement for batch update
        String sql = "INSERT INTO subjects (uuid, name) VALUES (?, ?)";

        // Perform batch update
        jdbcTemplate.batchUpdate(sql, batchParameters);
    }

}
