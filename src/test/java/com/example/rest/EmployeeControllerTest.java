package com.example.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    protected MockMvc mvc;

    @SneakyThrows
    @Test
    public void shouldReturnAllEmployees() {
        mvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Olga")));
    }

    @SneakyThrows
    @Test
    public void shouldReturnSalaryForEmployeeById() {
        mvc.perform(get("/salary/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print());
    }
}
