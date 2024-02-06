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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.FetchEntriesController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.body.FetchEntriesRequestBody;
import com.underpressure.backend.responses.EntryDataDto;

@WebMvcTest(FetchEntriesController.class)
public class FetchEntriesControllerTests extends ControllerTests {

    private String subjectName = "Subject";

    private FetchEntriesRequestBody requestBody = new FetchEntriesRequestBody(subjectName);

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

        mockMvc.perform(MockMvcRequestBuilders.post("/personal/entries")
                .header("Authorization", "Bearer id_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(entriesMock)));

    }

}
