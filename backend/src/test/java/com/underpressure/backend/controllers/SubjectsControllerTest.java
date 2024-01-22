package com.underpressure.backend.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;

@WebMvcTest(controllers = SubjectsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SubjectsControllerTest {

    @MockBean
    JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Should_Succeed_On_Valid_Request() throws Exception {
        String sql = "SELECT name FROM subjects";
        List<String> subjects = Arrays.asList(new String[] { "S1, S2, S3" });

        when(jdbcTemplate.queryForList(sql, String.class)).thenReturn(subjects);

        ResultActions response = mockMvc.perform(get("/subjects"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        CoreMatchers.instanceOf(List.class)));
    }

}
