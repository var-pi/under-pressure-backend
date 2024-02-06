package com.underpressure.backend.controllers;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.abstracts.ControllerTests;
import com.underpressure.backend.requests.params.GetSubjectsParams;
import com.underpressure.backend.services.database.DatabaseService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({
        FetchSubjectsController.class,
        DatabaseService.class
})
@Sql({
        "classpath:createSubjectsTable.sql",
        "classpath:fillSubjectsTable.sql"
})
public class FetchSubjectsControllerTests extends ControllerTests<FetchSubjectsController> {

    @Test
    public void Should_Succeed_When_Request_Valid() {

        ResponseEntity<List<String>> responseEntity = controller.handle(new GetSubjectsParams());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(3);
        assertThat(responseEntity.getBody().get(2)).isEqualTo("Subject 3");

    }

}
