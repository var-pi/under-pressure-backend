package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.UnfollowSubjectController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.data.UnfollowSubjectRequestData;

@WebMvcTest(UnfollowSubjectController.class)
public class UnfollowSubjectControllerTest extends ControllerTests {

    private UnfollowSubjectRequestData requestData = new UnfollowSubjectRequestData("Subject");

    @BeforeEach
    private void setUp() {

        doNothing().when(applicationServiceMock)
                .unfollowSubject(anyString(), any());
    }

    @Test
    public void Should_Unfollow_Subject() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/subjects/" + requestData.getSubjectName())
                .header("Authorization", "Bearer id_token"))
                .andExpect(status().isNoContent());

    }

}
