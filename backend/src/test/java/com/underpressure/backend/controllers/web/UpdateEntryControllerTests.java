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

import com.underpressure.backend.controllers.UpdateEntryController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.requests.path_variables.UpdateEntryRequestPathVariables;

@WebMvcTest(UpdateEntryController.class)
public class UpdateEntryControllerTests extends ControllerTests {

    private UpdateEntryRequestBody requestBody = new UpdateEntryRequestBody(0);
    private UpdateEntryRequestPathVariables requestPathVariables = new UpdateEntryRequestPathVariables("Subject");

    @BeforeEach
    private void setUp() {

        doNothing().when(applicationServiceMock)
                .updateEntry(anyString(), any());

    }

    @Test
    public void Should_Update_Entry() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/subjects/" +
                requestPathVariables.getSubjectName() + "/entries")
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNoContent());

    }

}
