package com.underpressure.backend.controllers.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.FetchEntriesController;
import com.underpressure.backend.controllers.data.abstracts.AuthorizedControllerTests;
import com.underpressure.backend.exceptions.auth.BearerTokenNullException;
import com.underpressure.backend.exceptions.does_not_exist.SubjectDoesNotExist;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;
import com.underpressure.backend.exceptions.parameter.SubjectNameParameterException;
import com.underpressure.backend.requests.data.FetchEntriesRequestData;
import com.underpressure.backend.requests.path_variables.FetchEntriesPathVariables;
import com.underpressure.backend.responses.EntryDataDto;
import com.underpressure.backend.services.database.DatabaseService;

@Import({
                FetchEntriesController.class,
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
public class FetchEntriesControllerTests extends AuthorizedControllerTests<FetchEntriesController> {

        @Test
        public void Should_Result_In_UNAUTHORIZED_When_BearerToken_Null() {

                FetchEntriesPathVariables pathVariables = new FetchEntriesPathVariables("Subject 1");

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller.handle(null, pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_BAD_REQUEST_When_SubjectName_Null() {

                FetchEntriesPathVariables pathVariables = new FetchEntriesPathVariables(null);

                SubjectNameParameterException ex = assertThrows(SubjectNameParameterException.class,
                                () -> controller.handle("Bearer user_1_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_User_Not_Found() {

                FetchEntriesPathVariables pathVariables = new FetchEntriesPathVariables("Subject 1");

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller
                                                .handle("Bearer user_4_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_Exception_When_Subject_Not_Found() {

                FetchEntriesPathVariables pathVariables = new FetchEntriesPathVariables("NaN");

                SubjectDoesNotExist ex = assertThrows(SubjectDoesNotExist.class,
                                () -> controller.handle("Bearer user_1_id_token", pathVariables));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Return_Entries_When_Request_Valid() {

                FetchEntriesPathVariables pathVariables = new FetchEntriesPathVariables("Subject 1");

                ResponseEntity<List<EntryDataDto>> responseEntity = controller
                                .handle("Bearer user_1_id_token", pathVariables);

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(responseEntity.getBody().size()).isEqualTo(2);

        }
}
