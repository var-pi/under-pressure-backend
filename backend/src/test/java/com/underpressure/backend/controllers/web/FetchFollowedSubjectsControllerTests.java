package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.underpressure.backend.controllers.FetchFollowedSubjectsController;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.database.FetchDatabase;
import com.underpressure.backend.services.database.Validate;
import com.underpressure.backend.services.google.FetchGoogle;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// @WebMvcTest(FetchFollowedSubjectsController.class)
@SpringBootTest
@AutoConfigureMockMvc // TODO isolate
public class FetchFollowedSubjectsControllerTests {

    // @Autowired
    // private MockMvc mockMvc;

    // @MockBean
    // private DatabaseService databaseServiceMock;

    // @MockBean
    // private GoogleService googleServiceMock;

    // @InjectMocks
    // private FetchFollowedSubjectsController controller;

    // Validate validateMock;

    // FetchGoogle fetchGoogleMock;

    // FetchDatabase fetchDatabaseMock;

    // List<String> subjectsMock = Arrays.asList("Subject 1", "Subject 2");

    // @BeforeEach
    // void setUp() {

    // validateMock = mock(Validate.class);
    // when(databaseServiceMock.validate()).thenReturn(validateMock);

    // doNothing().when(validateMock).bearerToken(anyString());

    // fetchGoogleMock = mock(FetchGoogle.class);
    // when(googleServiceMock.fetch()).thenReturn(fetchGoogleMock);

    // when(fetchGoogleMock.userId(anyString(), anyString())).thenReturn(123);

    // fetchDatabaseMock = mock(FetchDatabase.class);
    // when(databaseServiceMock.fetch()).thenReturn(fetchDatabaseMock);

    // when(fetchDatabaseMock.followedSubjects(anyInt())).thenReturn(subjectsMock);

    // }

    // @Test
    // public void Should_Fetch_Personal_Subjects() throws Exception {

    // mockMvc.perform(MockMvcRequestBuilders.post("/personal/subjects")
    // .contentType(MediaType.APPLICATION_JSON)
    // .header("Authorization", "Bearer id_token")
    // .content((new ObjectMapper()).writeValueAsString(subjectsMock)))
    // .andExpect(status().isOk())
    // .andExpect(content().json(new
    // ObjectMapper().writeValueAsString(subjectsMock)));

    // }

}
