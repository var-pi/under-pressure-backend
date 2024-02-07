package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.underpressure.backend.requests.data.FollowSubjectRequestData;

@WebMvcTest(FollowSubjectController.class)
public class FollowSubjectsControllerTests extends ControllerTests {

    FollowSubjectRequestData requestData = new FollowSubjectRequestData("Subject");

    @BeforeEach
    private void setUp() {

        doNothing().when(applicationServiceMock)
                .followSubject(anyString(), any());

    }

    @Test
    public void Should_Follow_Subject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/subjects/" + requestData.getSubjectName())
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FollowSubjectRequestBody(requestData))))
                .andExpect(status().isNoContent());

    }

}
