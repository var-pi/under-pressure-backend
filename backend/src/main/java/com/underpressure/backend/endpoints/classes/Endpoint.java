package com.underpressure.backend.endpoints.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Endpoint {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
