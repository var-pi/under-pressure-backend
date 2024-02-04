package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.AuthorizedControllerTests;
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;

@Import({
                FollowSubjectController.class,
                Fetch.class,
                Add.class,
                Check.class,
                Validate.class,
                Set.class,
                Extract.class
})
@Sql({
                "classpath:createSubjectsTable.sql",
                "classpath:fillSubjectsTable.sql",
                "classpath:createUsersTable.sql",
                "classpath:fillUsersTable.sql",
                "classpath:createSubjectInstancesTable.sql",
                "classpath:fillSubjectInstancesTable.sql"
})
public class FollowSubjectControllerTests extends AuthorizedControllerTests<FollowSubjectController> {

        @Test
        public void Should_Result_In_Bad_Request_When_IdToeknString_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new FollowSubjectRequestBody("Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_SubjectName_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new FollowSubjectRequestBody(null));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_User_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_4_id_token", new FollowSubjectRequestBody("Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Subject_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new FollowSubjectRequestBody("NaN"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_Requested_To_Follow_Already_Followed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new FollowSubjectRequestBody("Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Create_A_Subject_When_Request_Valid() {
                String idTokenString = "Bearer user_1_id_token";
                String subjectName = "Subject 3";

                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(idTokenString, new FollowSubjectRequestBody(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

                responseEntity = controller.handle(idTokenString, new FollowSubjectRequestBody(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Follow_A_Subject_When_Request_Valid() {
                String idTokenString = "Bearer user_2_id_token";
                String subjectName = "Subject 3";

                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(idTokenString, new FollowSubjectRequestBody(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

                responseEntity = controller.handle(idTokenString, new FollowSubjectRequestBody(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

}
