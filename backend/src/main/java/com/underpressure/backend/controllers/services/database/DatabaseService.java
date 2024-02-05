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

    public Update update() {
        return new UpdateImpl(jdbcTemplate);
    }

    public Validate validate() {
        return new ValidateImpl(this, jdbcTemplate);
    }

    public Set set() {
        return new SetImpl(jdbcTemplate);
    }

    public FetchDatabase fetch() {
        return new FetchDatabaseImpl(jdbcTemplate);
    }

}
