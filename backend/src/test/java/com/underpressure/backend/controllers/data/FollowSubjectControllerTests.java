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
import com.underpressure.backend.requests.body.FollowSubjectRequestBody;
import com.underpressure.backend.requests.path_variables.FollowSubjectPathVariables;
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

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables("Subject 1");

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller.handle(null, requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_SubjectName_Null() {

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables(null);

                SubjectNameParameterException ex = assertThrows(SubjectNameParameterException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token", requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_User_Not_Found() {

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables("Subject 1");

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller
                                                .handle("Bearer user_4_id_token", requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_Subject_Not_Found() {

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables("NaN");

                SubjectDoesNotExist ex = assertThrows(SubjectDoesNotExist.class, () -> controller
                                .handle("Bearer user_1_id_token", requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_Requested_To_Follow_Already_Followed() {

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables("Subject 1");

                SubjectAlreadyFollowedException ex = assertThrows(SubjectAlreadyFollowedException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token", requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Create_A_Subject_When_Request_Valid() {

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables("Subject 3");

                String bearerToken = "Bearer user_1_id_token";

                ResponseEntity<String> responseEntity = controller
                                .handle(bearerToken, requestBody, pathVariables);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                SubjectAlreadyFollowedException ex = assertThrows(SubjectAlreadyFollowedException.class,
                                () -> controller
                                                .handle(bearerToken, requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Follow_A_Subject_When_Request_Valid() {

                FollowSubjectRequestBody requestBody = new FollowSubjectRequestBody();
                FollowSubjectPathVariables pathVariables = new FollowSubjectPathVariables("Subject 3");

                String bearerToken = "Bearer user_2_id_token";

                ResponseEntity<String> responseEntity = controller
                                .handle(bearerToken, requestBody, pathVariables);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                SubjectAlreadyFollowedException ex = assertThrows(SubjectAlreadyFollowedException.class,
                                () -> controller
                                                .handle(bearerToken, requestBody, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

}
