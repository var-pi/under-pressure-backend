package com.underpressure.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.request.params.GetSubjectsParams;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import(GetSubjectsController.class)
@Sql({ "classpath:createSubjectsTable.sql", "classpath:fillSubjectsTable.sql" })
public class GetSubjectsControllerTests {

    @Autowired
    GetSubjectsController subjectsController;

    @Test
    public void Should_Succeed_When_Request_Valid() {

        ResponseEntity<ApiResponse<List<String>>> responseEntity = subjectsController.handle(new GetSubjectsParams());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(3);
        assertThat(responseEntity.getBody().getData().get(2)).isEqualTo("Subject 3");

    }

}
