package com.underpressure.backend.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.request.body.CreateUserRequestBody;
import com.underpressure.backend.controllers.classes.request.body.UnfollowSubjectsRequestBody;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import(CreateUserController.class)
@Sql({
        "classpath:createUsersTable.sql",
        "classpath:fillUsersTable.sql",
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CreateUserControllerTests {

    @Autowired
    CreateUserController controller;

    @Test
    public void Should_Result_In_Bad_Request_If_UserId_Null() {
        ResponseEntity<ApiResponse<String>> responseEntity = controller
                .handle(new CreateUserRequestBody(null));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Result_In_Bad_Request_If_User_Already_Exists() {
        ResponseEntity<ApiResponse<String>> responseEntity = controller
                .handle(new CreateUserRequestBody("User 1"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Create_User_If_Request_Valid() {
        String userId = "New User";

        ResponseEntity<ApiResponse<String>> responseEntity = controller
                .handle(new CreateUserRequestBody(userId));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

        responseEntity = controller
                .handle(new CreateUserRequestBody(userId));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
    }

}
