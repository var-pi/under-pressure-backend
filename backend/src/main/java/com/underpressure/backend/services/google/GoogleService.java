package com.underpressure.backend.services.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GoogleService {

    JdbcTemplate jdbcTemplate;

    public GoogleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FetchGoogle fetch() {
        return new FetchGoogleImpl(jdbcTemplate);
    }

}
