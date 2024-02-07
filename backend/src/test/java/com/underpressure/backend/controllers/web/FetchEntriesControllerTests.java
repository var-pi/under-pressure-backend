package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.FetchEntriesController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.path_variables.FetchEntriesPathVariables;
import com.underpressure.backend.responses.EntryDataDto;

@WebMvcTest(FetchEntriesController.class)
public class FetchEntriesControllerTests extends ControllerTests {

    FetchEntriesPathVariables pathVariables = new FetchEntriesPathVariables("Subject");

    private List<EntryDataDto> entriesMock = Arrays.asList(
            new EntryDataDto(new Date(123), 10),
            new EntryDataDto(new Date(456), 20));

    @BeforeEach
    void setUp() {

        when(applicationServiceMock.fetchEntries(anyString(),
                any())).thenReturn(entriesMock);

    }

    @Test
    public void Should_Fetch_Entries() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/subjects/" + pathVariables.getSubjectName() + "/entries")
                .header("Authorization", "Bearer id_token"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(entriesMock)));

    }

}
