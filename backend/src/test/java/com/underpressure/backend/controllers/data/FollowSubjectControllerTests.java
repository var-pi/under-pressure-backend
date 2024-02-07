package com.underpressure.backend.controllers.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.FollowSubjectController;
import com.underpressure.backend.controllers.data.abstracts.AuthorizedControllerTests;
import com.underpressure.backend.exceptions.already_exists.SubjectAlreadyFollowedException;
import com.underpressure.backend.exceptions.auth.BearerTokenNullException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.requests.data.FollowSubjectRequestData;
import com.underpressure.backend.services.database.DatabaseService;

@Import({
                FollowSubjectController.class,
                DatabaseService.class
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
        public void Should_Result_In_UNAUTHORIZED_When_BearerToken_Null() {

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller.handle(null, new FollowSubjectRequestData("Subject 1")));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_SubjectName_Null() {

                SubjectNameParameterException ex = assertThrows(SubjectNameParameterException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token", new FollowSubjectRequestData(null)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_User_Not_Found() {

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller
                                                .handle("Bearer user_4_id_token",
                                                                new FollowSubjectRequestData("Subject 1")));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_Subject_Not_Found() {

                SubjectDoesNotExist ex = assertThrows(SubjectDoesNotExist.class, () -> controller
                                .handle("Bearer user_1_id_token", new FollowSubjectRequestData("NaN")));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_Requested_To_Follow_Already_Followed() {

                SubjectAlreadyFollowedException ex = assertThrows(SubjectAlreadyFollowedException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token",
                                                                new FollowSubjectRequestData("Subject 1")));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Create_A_Subject_When_Request_Valid() {

                String bearerToken = "Bearer user_1_id_token";
                String subjectName = "Subject 3";

                ResponseEntity<String> responseEntity = controller
                                .handle(bearerToken, new FollowSubjectRequestData(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                SubjectAlreadyFollowedException ex = assertThrows(SubjectAlreadyFollowedException.class,
                                () -> controller
                                                .handle(bearerToken,
                                                                new FollowSubjectRequestData(subjectName)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Follow_A_Subject_When_Request_Valid() {

                String bearerToken = "Bearer user_2_id_token";
                String subjectName = "Subject 3";

                ResponseEntity<String> responseEntity = controller
                                .handle(bearerToken, new FollowSubjectRequestData(subjectName));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                SubjectAlreadyFollowedException ex = assertThrows(SubjectAlreadyFollowedException.class,
                                () -> controller
                                                .handle(bearerToken,
                                                                new FollowSubjectRequestData(subjectName)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

}
