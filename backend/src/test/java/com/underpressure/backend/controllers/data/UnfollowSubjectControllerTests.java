package com.underpressure.backend.controllers.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.UnfollowSubjectController;
import com.underpressure.backend.controllers.data.abstracts.AuthorizedControllerTests;
import com.underpressure.backend.exceptions.already_exists.SubjectUnfollowedException;
import com.underpressure.backend.exceptions.auth.BearerTokenNullException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.SubjectInstanceDoesNotExistsException;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.requests.path_variables.UnfollowSubjectPathVariables;
import com.underpressure.backend.services.database.DatabaseService;

@Import({
                UnfollowSubjectController.class,
                DatabaseService.class
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
        public void Should_Result_In_UNAUTHORIZED_When_BearerToken_Null() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                "Subject 1");

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller.handle(null, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_SubjectName_Null() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                null);

                SubjectNameParameterException ex = assertThrows(SubjectNameParameterException.class,
                                () -> controller.handle("Bearer user_1_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_User_Not_Found() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                "Subject 1");

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller.handle("Bearer user_4_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_Subject_Not_Found() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                "NaN");

                SubjectDoesNotExist ex = assertThrows(SubjectDoesNotExist.class,
                                () -> controller.handle("Bearer user_1_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_When_Request_To_Unfollow_Having_Never_Followed() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                "Subject 3");

                SubjectInstanceDoesNotExistsException ex = assertThrows(SubjectInstanceDoesNotExistsException.class,
                                () -> controller.handle("Bearer user_1_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_Requested_To_Unfollow_Already_Unfollowed() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                "Subject 3");

                SubjectUnfollowedException ex = assertThrows(SubjectUnfollowedException.class,
                                () -> controller.handle("Bearer user_2_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Unfollow_A_Subject_When_Request_Valid() {

                UnfollowSubjectPathVariables pathVariables = new UnfollowSubjectPathVariables(
                                "Subject 1");

                String bearerToken = "Bearer user_1_id_token";

                ResponseEntity<String> responseEntity = controller.handle(bearerToken, pathVariables);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                SubjectUnfollowedException ex = assertThrows(SubjectUnfollowedException.class,
                                () -> controller.handle(bearerToken, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

}
