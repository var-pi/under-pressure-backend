package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.request.body.AuthenticationBody;
import com.underpressure.backend.controllers.helpers.Add;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Fetch;

@JdbcTest
@AutoConfigureTestDatabase
@Import({ AuthenticationController.class, Fetch.Google.class, Add.class, Check.class })
@Sql({
        "classpath:createUsersTable.sql",
        "classpath:fillUsersTable.sql"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationControllerTests {

    @Autowired
    AuthenticationController controller;

    @Test
    public void Should_Result_In_Bad_Request_When_Code_Null() {
        ResponseEntity<ApiResponse<String>> responseEntity = controller
                .handle(new AuthenticationBody(null));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    // TODO Poorly tested.

}
