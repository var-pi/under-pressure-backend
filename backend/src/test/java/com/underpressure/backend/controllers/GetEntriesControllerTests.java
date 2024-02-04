package com.underpressure.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import com.underpressure.backend.controllers.classes.request.body.GetEntriesRequestBody;
import com.underpressure.backend.controllers.classes.request.data.EntryData;
import com.underpressure.backend.controllers.helpers.Check;
import com.underpressure.backend.controllers.helpers.Fetch;
import com.underpressure.backend.controllers.helpers.Validate;

@JdbcTest
@AutoConfigureTestDatabase
@Import({ GetEntriesController.class, Fetch.DB.class, Validate.class, Check.class })
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetEntriesControllerTests {

    @Autowired
    GetEntriesController controller;

    @Test
    public void Should_Result_In_Bad_Request_When_UserId_Null() {
        ResponseEntity<ApiResponse<List<EntryData>>> responseEntity = controller
                .handle(new GetEntriesRequestBody(null, "Subject 1"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Result_In_Bad_Request_When_SubjectName_Null() {
        ResponseEntity<ApiResponse<List<EntryData>>> responseEntity = controller
                .handle(new GetEntriesRequestBody(1, null));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Result_In_Not_Found_Exception_When_User_Not_Found() {
        ResponseEntity<ApiResponse<List<EntryData>>> responseEntity = controller
                .handle(new GetEntriesRequestBody(-1, "Subject 1"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Result_In_Not_Found_Exception_When_Subject_Not_Found() {
        ResponseEntity<ApiResponse<List<EntryData>>> responseEntity = controller
                .handle(new GetEntriesRequestBody(1, "NaN"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("fail");
        assertThat(responseEntity.getBody().getMessage()).isNotBlank();
    }

    @Test
    public void Should_Return_Entries_When_Request_Valid() {
        ResponseEntity<ApiResponse<List<EntryData>>> responseEntity = controller
                .handle(new GetEntriesRequestBody(1, "Subject 1"));

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
        assertThat(responseEntity.getBody().getData().size()).isEqualTo(2);
    }
}
