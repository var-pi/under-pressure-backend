package com.underpressure.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FollowedSubjectsController.class)
@Sql({
        "classpath:createSubjectsTable.sql",
        "classpath:fillSubjectsTable.sql",
        "classpath:createUsersTable.sql",
        "classpath:fillUsersTable.sql",
        "classpath:createSubjectInstancesTable.sql",
        "classpath:fillSubjectInstancesTable.sql" })
public class FollowedSubjectsControllerTests {

    @Autowired
    FollowedSubjectsController followedSubjectsController;

    @Test
    public void Should_Succeed_On_Valid_Request() {
    }

}
