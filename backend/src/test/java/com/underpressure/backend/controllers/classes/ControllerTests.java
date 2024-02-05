package com.underpressure.backend.controllers.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import com.underpressure.backend.controllers.helpers.Fetch;

@JdbcTest
@AutoConfigureTestDatabase
@Import({ Fetch.DB.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerTests<T> {

    @Autowired
    protected T controller;

}
