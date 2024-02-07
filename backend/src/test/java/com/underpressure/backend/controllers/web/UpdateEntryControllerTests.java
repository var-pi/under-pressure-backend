package com.underpressure.backend.controllers.web;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.UpdateEntryController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.requests.data.UpdateEntryRequestData;

@WebMvcTest(UpdateEntryController.class)
public class UpdateEntryControllerTests extends ControllerTests {

    private UpdateEntryRequestData requestData = new UpdateEntryRequestData("Subject", 10);

    @BeforeEach
    private void setUp() {

        doNothing().when(applicationServiceMock)
                .updateEntry("Bearer id_token", requestData);

    }

    @Test
    public void Should_Update_Entry() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/subjects/" + requestData.getSubjectName() + "/entries")
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UpdateEntryRequestBody(requestData))))
                .andExpect(status().isNoContent());

    }

}
