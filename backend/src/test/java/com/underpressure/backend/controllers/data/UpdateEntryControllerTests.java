package com.underpressure.backend.controllers.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.UpdateEntryController;
import com.underpressure.backend.controllers.data.abstracts.AuthorizedControllerTests;
import com.underpressure.backend.exceptions.already_exists.SubjectUnfollowedException;
import com.underpressure.backend.exceptions.auth.BearerTokenNullException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.SubjectInstanceDoesNotExistsException;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.StressLevelParameterException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.exceptions.range.StressLevelRangeException;
import com.underpressure.backend.requests.body.AddEntryRequestBody;
import com.underpressure.backend.services.database.DatabaseService;

@Import({
                UpdateEntryController.class,
                DatabaseService.class
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
        public void Should_Result_In_UNAUTHORIZED_When_BearerToken_Null() {

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller
                                                .handle(null, new AddEntryRequestBody("Subject 1", 0)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_SubjectName_Null() {

                SubjectNameParameterException ex = assertThrows(SubjectNameParameterException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token",
                                                                new AddEntryRequestBody(null, 0)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Bad_REQUEST_When_StressLevel_Null() {

                StressLevelParameterException ex = assertThrows(StressLevelParameterException.class, () -> controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("Subject 1",
                                                null)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_User_Not_Found() {

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller
                                                .handle("Bearer user_4_id_token",
                                                                new AddEntryRequestBody("Subject 1", 0)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_Subject_Not_Found() {

                SubjectDoesNotExist ex = assertThrows(SubjectDoesNotExist.class, () -> controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("NaN", 0)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Stress_Level_Out_Of_Range() {

                StressLevelRangeException ex = assertThrows(StressLevelRangeException.class, () -> controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("Subject 1", -1)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Bad_Request_When_Unfollowed() {

                SubjectUnfollowedException ex = assertThrows(SubjectUnfollowedException.class, () -> controller
                                .handle("Bearer user_2_id_token", new AddEntryRequestBody("Subject 3", 0)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Bad_Request_When_Not_Followed() {

                SubjectInstanceDoesNotExistsException ex = assertThrows(SubjectInstanceDoesNotExistsException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token",
                                                                new AddEntryRequestBody("Subject 3", 0)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Add_Entry_When_Request_Valid() {

                ResponseEntity<String> responseEntity = controller
                                .handle("Bearer user_1_id_token", new AddEntryRequestBody("Subject 2", 40));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        }

        @Test
        public void Should_Update_Entry_When_Request_Valid() {

                String bearerToken = "Bearer user_1_id_token";
                controller
                                .handle(bearerToken, new AddEntryRequestBody("Subject 1", 40));

                ResponseEntity<String> responseEntity = controller
                                .handle(bearerToken, new AddEntryRequestBody("Subject 1", 50));

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        }
}
