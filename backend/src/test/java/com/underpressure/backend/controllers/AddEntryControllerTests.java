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
import com.underpressure.backend.controllers.classes.request.body.AddEntryRequestBody;

@JdbcTest
@AutoConfigureTestDatabase
@Import(AddEntryController.class)
@Sql({
                "classpath:createSubjectsTable.sql",
                "classpath:fillSubjectsTable.sql",
                "classpath:createUsersTable.sql",
                "classpath:fillUsersTable.sql",
                "classpath:createSubjectInstancesTable.sql",
                "classpath:fillSubjectInstancesTable.sql",
                "classpath:createEntriesTable.sql",
                "classpath:fillEntriesTable.sql"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddEntryControllerTests {

        @Autowired
        AddEntryController controller;

        @Test
        public void Should_Result_In_Bad_Request_When_UserId_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(null, "Subject 1", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_SubjectName_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(1, null, 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_StressLevel_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(1, "Subject 1", null));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_User_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(-1, "Subject 1", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Subject_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(1, "NaN", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Stress_Level_Out_Of_Range() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(1, "Subject 1", 101));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_Not_Followed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(2, "Subject 3", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Add_Entry_When_Request_Valid() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(1, "Subject 2", 40));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        }

        @Test
        public void Should_Update_Entry_When_Request_Valid() {
                controller
                                .handle(new AddEntryRequestBody(1, "Subject 1", 40));

                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new AddEntryRequestBody(1, "Subject 1", 50));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
}
