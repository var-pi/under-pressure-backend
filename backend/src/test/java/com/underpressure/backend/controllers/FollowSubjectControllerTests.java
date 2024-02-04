package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.request.body.FollowSubjectRequestBody;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.FetchStatic;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.unexpected.UserVerificationException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature.Header;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;
import static org.mockito.Mockito.*;

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

        @MockBean
        Fetch.Google fetchGoogleMock;

        @Autowired
        FollowSubjectController controller;

        @BeforeEach
        public void setUp() throws UserVerificationException {
                when(fetchGoogleMock.googleSub(any(), any())).thenReturn("10001");
                when(fetchGoogleMock.userId(any(), any(), any())).thenReturn(1);
        }

        @Test
        public void Should_Result_In_Bad_Request_When_IdToeknString_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody(null, "Subject 1"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_SubjectName_Null() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("id_token", null));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Subject_Not_Found() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("id_token", "NaN"));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        @Test
        public void Should_Result_In_Bad_Request_When_Requested_To_Follow_Already_Followed() {
                ResponseEntity<ApiResponse<String>> responseEntity = controller
                                .handle(new FollowSubjectRequestBody("id_token", "Subject 1"));

                assertThat(responseEntity.getBody().getMessage()).isBlank();
                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
                assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        }

        // @Test
        // public void Should_Create_A_Subject_When_Request_Valid() {
        // Integer userId = 1;
        // String subjectName = "Subject 3";

        // ResponseEntity<ApiResponse<String>> responseEntity = controller
        // .handle(new FollowSubjectRequestBody(userId, subjectName));

        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

        // responseEntity = controller.handle(new FollowSubjectRequestBody(userId,
        // subjectName));

        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        // assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        // }

        // @Test
        // public void Should_Follow_A_Subject_When_Request_Valid() {
        // Integer userId = 2;
        // String subjectName = "Subject 3";

        // ResponseEntity<ApiResponse<String>> responseEntity = controller
        // .handle(new FollowSubjectRequestBody(userId, subjectName));

        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        // assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");

        // responseEntity = controller.handle(new FollowSubjectRequestBody(userId,
        // subjectName));

        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        // assertThat(responseEntity.getBody().getMessage()).isNotBlank();
        // }

}
