package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.AuthorizedControllerTests;
import com.underpressure.backend.controllers.classes.request.body.AddEntryRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Update;
import com.underpressure.backend.controllers.helpers.Validate;

@Import({
                UpdateEntryController.class,
                Add.class,
                Check.class,
                Validate.class,
                Update.class
})
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
public class UpdateEntryControllerTests extends AuthorizedControllerTests<UpdateEntryController> {

        @Test
        public void Should_Result_In_Bad_Request_When_IdToeknString_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(null, new AddEntryRequestBody("Subject 1", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_SubjectName_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody(null, 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_StressLevel_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("Subject 1", null));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_User_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_4_id_token", new AddEntryRequestBody("Subject 1", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Subject_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("NaN", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Stress_Level_Out_Of_Range() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("Subject 1", 101));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_Not_Followed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_2_id_token", new AddEntryRequestBody("Subject 3", 0));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Add_Entry_When_Request_Valid() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("Subject 2", 40));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        }

        @Test
        public void Should_Update_Entry_When_Request_Valid() {
                String bearerToken = "Bearer user_1_id_token";
                controller
                                .handle(bearerToken, new AddEntryRequestBody("Subject 1", 40));

                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(bearerToken, new AddEntryRequestBody("Subject 1", 50));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
}
