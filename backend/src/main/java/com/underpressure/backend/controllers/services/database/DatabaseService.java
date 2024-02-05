package com.underpressure.backend.controllers.services.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Add add() {
        return new AddImpl(jdbcTemplate);
    }

    public Check check() {
        return new CheckImpl(jdbcTemplate);
    }

}
