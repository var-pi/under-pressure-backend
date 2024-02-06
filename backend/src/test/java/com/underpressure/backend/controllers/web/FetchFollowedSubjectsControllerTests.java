package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.FetchFollowedSubjectsController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;

@WebMvcTest(FetchFollowedSubjectsController.class)
public class FetchFollowedSubjectsControllerTests extends ControllerTests {

    private List<String> subjectsMock = Arrays.asList("Subject 1", "Subject 2");

    @BeforeEach
    void setUp() {

        when(applicationServiceMock.fetchFollowedSubjects(anyString(), any())).thenReturn(subjectsMock);

    }

    @Test
    public void Should_Fetch_Personal_Subjects() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/subjects")
                .header("Authorization", "Bearer id_token"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(subjectsMock)));

    }

}
