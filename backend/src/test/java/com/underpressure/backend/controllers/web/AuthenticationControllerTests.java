package com.underpressure.backend.controllers.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.underpressure.backend.controllers.AuthenticationController;
import com.underpressure.backend.controllers.web.abstracts.ControllerTests;
import com.underpressure.backend.requests.body.AuthenticationRequestBody;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTests extends ControllerTests {

    private AuthenticationRequestBody requestBody = new AuthenticationRequestBody("code");
    private String idTokenString = "idToken";

    @BeforeEach
    private void setUp() {

        when(applicationServiceMock.authenticate(any())).thenReturn(idTokenString);

    }

    @Test
    public void Should_Authenticate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody.getCode())))
                .andExpect(status().isOk())
                .andExpect(content().string(idTokenString));

    }

}
