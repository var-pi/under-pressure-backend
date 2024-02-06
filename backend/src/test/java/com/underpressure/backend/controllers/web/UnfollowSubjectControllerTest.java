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

import com.underpressure.backend.controllers.UnfollowSubjectController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.body.UnfollowSubjectRequestBody;

@WebMvcTest(UnfollowSubjectController.class)
public class UnfollowSubjectControllerTest extends ControllerTests {

    private String subjectName = "Subject";

    @BeforeEach
    private void setUp() {

        doNothing().when(applicationServiceMock)
                .unfollowSubject(anyString(), eq(new UnfollowSubjectRequestBody(subjectName)));
    }

    @Test
    public void Should_Unfollow_Subject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/personal/subjects/unfollow")
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UnfollowSubjectRequestBody(subjectName))))
                .andExpect(status().isNoContent());

    }

}
