package com.underpressure.backend.controllers.web.abstracts;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.underpressure.backend.services.application.ApplicationService;

public class ControllerTests {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ApplicationService applicationServiceMock;

    protected ObjectMapper objectMapper;

    @BeforeEach
    void abstractSetUp() {

        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    }
}
