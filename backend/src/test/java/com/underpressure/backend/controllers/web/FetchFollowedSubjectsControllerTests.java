package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.underpressure.backend.controllers.FetchFollowedSubjectsController;
import com.underpressure.backend.requests.body.FetchFollowedSubjectsRequestBody;
import com.underpressure.backend.services.application.ApplicationService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(FetchFollowedSubjectsController.class)
public class FetchFollowedSubjectsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationService applicationServiceMock;

    private List<String> subjectsMock = Arrays.asList("Subject 1", "Subject 2");

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        when(applicationServiceMock.fetchFollowedSubjects(anyString())).thenReturn(subjectsMock);

        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    }

    @Test
    public void Should_Fetch_Personal_Subjects() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/personal/subjects")
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FetchFollowedSubjectsRequestBody())))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(subjectsMock)));

    }

}
