package com.underpressure.backend.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;

import static org.assertj.core.api.Assertions.assertThat;

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
    FollowedSubjectsController controller;

    @Test
    public void Should_Succeed_On_Valid_Request() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle(new FollowedSubjectsRequestBody("User 1"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(2);
    }

    @Test
    public void Should_Return_Not_Found_On_Nonexistant_User() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle(new FollowedSubjectsRequestBody("User Nan"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotEmpty();
    }

}
