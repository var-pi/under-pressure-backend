package com.underpressure.backend.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.AuthorizedControllerTests;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Validate;
import com.underpressure.backend.exceptions.auth.BearerTokenNullException;
import com.underpressure.backend.exceptions.does_not_exist.UserDoesNotExistException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import({
                FetchFollowedSubjectsController.class,
                Validate.class,
                Check.class
})
@Sql({
                "classpath:createSubjectsTable.sql",
                "classpath:fillSubjectsTable.sql",
                "classpath:createUsersTable.sql",
                "classpath:fillUsersTable.sql",
                "classpath:createSubjectInstancesTable.sql",
                "classpath:fillSubjectInstancesTable.sql"
})
public class FetchFollowedSubjectsControllerTests extends AuthorizedControllerTests<FetchFollowedSubjectsController> {

        @Test
        public void Should_Result_In_UNAUTHORIZED_When_BearerToken_Null() {

                BearerTokenNullException ex = assertThrows(BearerTokenNullException.class,
                                () -> controller.handle(null, new FollowedSubjectsRequestBody()));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Result_In_NOT_FOUND_When_User_Does_Not_Exist() {

                UserDoesNotExistException ex = assertThrows(UserDoesNotExistException.class,
                                () -> controller.handle("Bearer user_4_id_token", new FollowedSubjectsRequestBody()));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
                assertThat(ex.getMessage()).isNotBlank();

        }

        @Test
        public void Should_Return_Followed_Subjects_When_Request_Valid() {

                ResponseEntity<List<String>> responseEntity = controller
                                .handle("Bearer user_1_id_token", new FollowedSubjectsRequestBody());

                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(responseEntity.getBody().size()).isEqualTo(2);
        }

}
