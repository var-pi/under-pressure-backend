package com.underpressure.backend.controllers.classes.abstracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Controller {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
