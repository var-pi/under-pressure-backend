package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FollowSubjectController.class)
@Sql({
                "classpath:createSubjectsTable.sql",
                "classpath:fillSubjectsTable.sql",
                "classpath:createUsersTable.sql",
                "classpath:fillUsersTable.sql",
                "classpath:createSubjectInstancesTable.sql",
                "classpath:fillSubjectInstancesTable.sql"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FollowSubjectControllerTests {

        @Autowired
        FollowSubjectController controller;

        @Test
        public void Should_Result_In_Bad_Request_If_UserId_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody(null, "Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_If_SubjectName_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("User 1", null));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_If_User_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("NaN", "Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_If_Subject_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("User 1", "NaN"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_On_Request_To_Follow_Already_Followed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("User 1", "Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Follow_A_Subject_On_Valid_Request() {
                String userId = "User 1";
                String subjectName = "Subject 3";

                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody(userId, subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

                responseEntity = controller.handle(new FollowSubjectRequestBody(userId, subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }
}
