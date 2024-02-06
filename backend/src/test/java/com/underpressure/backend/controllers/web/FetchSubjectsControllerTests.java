package com.underpressure.backend.controllers.web;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.FetchSubjectsController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(FetchSubjectsController.class)
public class FetchSubjectsControllerTests extends ControllerTests {

    private List<String> subjectsMock = Arrays.asList("Subject 1", "Subject 2");

    @BeforeEach
    private void setUp() {

        when(applicationServiceMock.fetchSubjects()).thenReturn(subjectsMock);

    }

    @Test
    public void Should_Fetch_Subjects() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/subjects/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subjectsMock)));

    }

}
