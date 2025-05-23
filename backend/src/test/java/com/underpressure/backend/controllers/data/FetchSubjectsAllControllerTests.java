package com.underpressure.backend.controllers.data;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.underpressure.backend.controllers.FetchSubjectsAllController;
import com.underpressure.backend.controllers.data.abstracts.ControllerTests;
import com.underpressure.backend.requests.path_variables.FetchSubjectsAllPathVariables;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({
        FetchSubjectsAllController.class
})
@Sql({
        "classpath:createSubjectsTable.sql",
        "classpath:fillSubjectsTable.sql"
})
public class FetchSubjectsAllControllerTests extends ControllerTests<FetchSubjectsAllController> {

    FetchSubjectsAllPathVariables pathVariables = new FetchSubjectsAllPathVariables();

    @Test
    public void Should_Succeed_When_Request_Valid() {

        ResponseEntity<List<String>> responseEntity = controller.handle(pathVariables);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(3);
        assertThat(responseEntity.getBody().get(2)).isEqualTo("Subject 3");

    }

}
