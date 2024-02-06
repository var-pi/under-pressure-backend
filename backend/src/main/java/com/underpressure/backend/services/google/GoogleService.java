package com.underpressure.backend.services.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GoogleService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public FetchGoogle fetch() {
        return new FetchGoogleImpl(jdbcTemplate);
    }

}
