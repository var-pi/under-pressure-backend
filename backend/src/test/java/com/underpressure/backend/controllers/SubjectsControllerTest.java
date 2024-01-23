package com.underpressure.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import(SubjectsController.class)
@Sql({ "classpath:createUsersTable.sql", "classpath:fillUsersTable.sql" })
public class SubjectsControllerTest {

    @Autowired
    SubjectsController subjectsController;

    @Test
    public void Should_Succeed_On_Valid_Request() {

        ResponseEntity<ApiResponse<List<String>>> responseEntity = subjectsController.handle();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(3);
        assertThat(responseEntity.getBody().getData().get(2)).isEqualTo("Subject 2");

    }

}
