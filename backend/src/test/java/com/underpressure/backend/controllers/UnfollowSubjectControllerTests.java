package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.AuthorizedControllerTests;
import com.underpressure.backend.controllers.classes.request.body.UnfollowSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Set;
import com.underpressure.backend.controllers.helpers.Validate;

@Import({
                UnfollowSubjectController.class,
                Validate.class,
                Check.class,
                Set.class
})
@Sql({
                "classpath:createSubjectsTable.sql",
                "classpath:fillSubjectsTable.sql",
                "classpath:createUsersTable.sql",
                "classpath:fillUsersTable.sql",
                "classpath:createSubjectInstancesTable.sql",
                "classpath:fillSubjectInstancesTable.sql" })
public class UnfollowSubjectControllerTests extends AuthorizedControllerTests<UnfollowSubjectController> {

        @Test
        public void Should_Result_In_Bad_Request_When_BearerToken_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(null, new UnfollowSubjectsRequestBody("Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_SubjectName_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new UnfollowSubjectsRequestBody(null));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_User_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_4_id_token", new UnfollowSubjectsRequestBody("Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Subject_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new UnfollowSubjectsRequestBody("NaN"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_Request_To_Unfollow_Having_Never_Followed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new UnfollowSubjectsRequestBody("Subject 3"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_Requested_To_Unfollow_Already_Unfollowed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle("Bearer user_2_id_token", new UnfollowSubjectsRequestBody("Subject 3"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Unfollow_A_Subject_When_Request_Valid() {
                String bearerToken = "Bearer user_1_id_token";
                String subjectName = "Subject 1";

                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(bearerToken, new UnfollowSubjectsRequestBody(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

                responseEntity = controller
                                .handle(bearerToken, new UnfollowSubjectsRequestBody(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

}
