package com.example.Payroll;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        repository.deleteAll(); // Clear repository before each test
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // Given
        Employee employee1 = new Employee(null, "John Doe", "Developer");
        Employee employee2 = new Employee(null, "Jane Smith", "Manager");
        repository.saveAll(Arrays.asList(employee1, employee2));

        // When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        List<Employee> employees = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Employee[].class));
        assert employees != null;
        assert employees.size() == 2;
        assert employees.get(0).getName().equals("John Doe");
        assert employees.get(1).getName().equals("Jane Smith");
    }
}

