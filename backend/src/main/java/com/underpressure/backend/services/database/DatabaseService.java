package com.underpressure.backend.services.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Add add() { // TODO Ca I use @Autowired here?
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
