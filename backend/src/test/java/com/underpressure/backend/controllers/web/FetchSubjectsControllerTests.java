package com.underpressure.backend.controllers.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.underpressure.backend.controllers.FetchSubjectsController;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.database.FetchDatabase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;;

@SpringBootTest
@AutoConfigureMockMvc
public class FetchSubjectsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseService databaseServiceMock;

    @InjectMocks
    private FetchSubjectsController controller;

    FetchDatabase fetchDatabaseMock;

    List<String> expectedSubjects;

    @BeforeEach
    void setUp() {
        fetchDatabaseMock = mock(FetchDatabase.class);
        when(databaseServiceMock.fetch()).thenReturn(fetchDatabaseMock);

        expectedSubjects = Arrays.asList("Subject 1", "Subject 2");
        when(fetchDatabaseMock.subjects()).thenReturn(expectedSubjects);
    }

    @Test
    public void testHelloEndpoint() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedSubjects)));

    }

}
