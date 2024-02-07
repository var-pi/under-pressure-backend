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
import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.requests.pathVariables.UpdateEntryRequestPathVariables;
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

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 1");

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller
                                                .handle(null, requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_SubjectName_Null() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables(null);

                SubjectNameParameterException ex = assertThrows(SubjectNameParameterException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Bad_REQUEST_When_StressLevel_Null() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody((Integer) null);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 1");

                StressLevelParameterException ex = assertThrows(StressLevelParameterException.class, () -> controller
                                .handle("Bearer user_1_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_User_Not_Found() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 1");

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller
                                                .handle("Bearer user_4_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_Subject_Not_Found() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("NaN");

                SubjectDoesNotExist ex = assertThrows(SubjectDoesNotExist.class, () -> controller
                                .handle("Bearer user_1_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Not_Found_Exception_When_Stress_Level_Out_Of_Range() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(-1);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 1");

                StressLevelRangeException ex = assertThrows(StressLevelRangeException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Bad_Request_When_Unfollowed() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 3");

                SubjectUnfollowedException ex = assertThrows(SubjectUnfollowedException.class, () -> controller
                                .handle("Bearer user_2_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_Bad_Request_When_Not_Followed() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 3");

                SubjectInstanceDoesNotExistsException ex = assertThrows(SubjectInstanceDoesNotExistsException.class,
                                () -> controller
                                                .handle("Bearer user_1_id_token", requestBody, requestPathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Add_Entry_When_Request_Valid() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 1");

                ResponseEntity<String> responseEntity = controller
                                .handle("Bearer user_1_id_token", requestBody, requestPathVariables);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        }

        @Test
        public void Should_Update_Entry_When_Request_Valid() {

                UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
                UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject 1");

                String bearerToken = "Bearer user_1_id_token";
                controller
                                .handle(bearerToken, requestBody, requestPathVariables);

                ResponseEntity<String> responseEntity = controller
                                .handle(bearerToken, requestBody, requestPathVariables);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        }
}
