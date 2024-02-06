package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.classes.request.body.AuthenticationBody;
import com.underpressure.backend.exceptions.parameter.CodeParameterException;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
                AuthenticationController.class,
                DatabaseService.class,
                GoogleService.class
})
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

                CodeParameterException ex = assertThrows(CodeParameterException.class, () -> controller
                                .handle(new AuthenticationBody(null)));

                assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
                assertThat(ex.getMessage()).isNotBlank();

        }

        // TODO Poorly tested.

}
