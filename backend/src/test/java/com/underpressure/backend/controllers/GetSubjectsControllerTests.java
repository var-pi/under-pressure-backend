package com.underpressure.backend.controllers;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.ControllerTests;
import com.underpressure.backend.controllers.classes.request.params.GetSubjectsParams;
import com.underpressure.backend.controllers.helpers.Fetch;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({ GetSubjectsController.class, Fetch.DB.class })
@Sql({ "classpath:createSubjectsTable.sql", "classpath:fillSubjectsTable.sql" })
public class GetSubjectsControllerTests extends ControllerTests<GetSubjectsController> {

    @Test
    public void Should_Succeed_When_Request_Valid() {

        ResponseEntity<ApiResponse<List<String>>> responseEntity = controller.handle(new GetSubjectsParams());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(3);
        assertThat(responseEntity.getBody().getData().get(2)).isEqualTo("Subject 3");

    }

}
