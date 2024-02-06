package com.underpressure.backend.controllers.web;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.underpressure.backend.controllers.FetchSubjectsController;
import com.underpressure.backend.services.application.ApplicationService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(FetchSubjectsController.class)
public class FetchSubjectsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationService;

    private List<String> subjectsMock = Arrays.asList("Subject 1", "Subject 2");

    @BeforeEach
    void setUp() {
        when(applicationService.fetchSubjects()).thenReturn(subjectsMock);
    }

    @Test
    public void Should_Fetch_Subjects() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(subjectsMock)));

    }

}
