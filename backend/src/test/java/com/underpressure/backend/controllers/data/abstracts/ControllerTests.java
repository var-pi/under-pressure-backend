package com.underpressure.backend.controllers.data.abstracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import com.underpressure.backend.services.application.ApplicationService;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        ApplicationService.class,
        UtilityService.class,
        GoogleService.class,
        DatabaseService.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class ControllerTests<T> {

    @Autowired
    protected T controller;

}
