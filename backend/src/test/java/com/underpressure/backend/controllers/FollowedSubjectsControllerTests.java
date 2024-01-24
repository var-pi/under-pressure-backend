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
        "classpath:fillSubjectInstancesTable.sql"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FollowedSubjectsControllerTests {

    @Autowired
    FollowedSubjectsController controller;

    @Test
    public void Should_Result_In_Bad_Request_If_UserId_Null() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle(new FollowedSubjectsRequestBody(null));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Result_In_Not_Found_exception_If_User_Not_Found() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle(new FollowedSubjectsRequestBody("NaN"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotEmpty();
    }

    @Test
    public void Should_Return_Followed_Subjects_On_Valid_Request() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle(new FollowedSubjectsRequestBody("User 1"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(2);
    }

}
