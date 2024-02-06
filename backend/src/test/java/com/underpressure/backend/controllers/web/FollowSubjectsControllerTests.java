package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.FollowSubjectController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.body.FollowSubjectRequestBody;

@WebMvcTest(FollowSubjectController.class)
public class FollowSubjectsControllerTests extends ControllerTests {

    private String subjectName = "Subject";

    @BeforeEach
    private void setUp() {

        doNothing().when(applicationServiceMock)
                .followSubject(anyString(), eq(new FollowSubjectRequestBody(subjectName)));

    }

    @Test
    public void Should_Follow_Subject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/personal/subjects/follow")
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FollowSubjectRequestBody(subjectName))))
                .andExpect(status().isNoContent());

    }

}
