package com.underpressure.backend.controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.AuthorizedControllerTests;
import com.underpressure.backend.controllers.classes.request.body.FollowedSubjectsRequestBody;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Extract;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Validate;

import static org.assertj.core.api.Assertions.assertThat;

@Import({
        FollowedSubjectsController.class,
        Fetch.DB.class,
        Fetch.Google.class,
        Validate.class,
        Check.class,
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
public class FollowedSubjectsControllerTests extends AuthorizedControllerTests<FollowedSubjectsController> {

    @Test
    public void Should_Result_In_Bad_Request_When_UserId_Null() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle(null, new FollowedSubjectsRequestBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Result_In_Not_Found_exception_When_User_Not_Found() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle("Bearer user_4_id_token", new FollowedSubjectsRequestBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotEmpty();
    }

    @Test
    public void Should_Return_Followed_Subjects_When_Request_Valid() {
        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller
                .handle("Bearer user_1_id_token", new FollowedSubjectsRequestBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(2);
    }

}
