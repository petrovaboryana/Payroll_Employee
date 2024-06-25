package com.example.Payroll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setRole("Developer");
        repository.save(employee);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].role", is("Developer")));
    }

    @Test
    public void testCreateNewEmployee() throws Exception {
        String newEmployeeJson = "{\"name\":\"Jane Doe\",\"role\":\"Manager\"}";

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Jane Doe")))
                .andExpect(jsonPath("$.role", is("Manager")));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setRole("Developer");
        employee = repository.save(employee);

        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.role", is("Developer")));
    }

    @Test
    public void testGetEmployeeById_NotFound() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setRole("Developer");
        employee = repository.save(employee);

        String updatedEmployeeJson = "{\"name\":\"John Smith\",\"role\":\"Manager\"}";

        mockMvc.perform(put("/employees/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")))
                .andExpect(jsonPath("$.role", is("Manager")));
    }

    @Test
    public void testUpdateEmployee_CreateNew() throws Exception {
        String updatedEmployeeJson = "{\"name\":\"John Smith\",\"role\":\"Manager\"}";

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Smith")))
                .andExpect(jsonPath("$.role", is("Manager")));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setRole("Developer");
        employee = repository.save(employee);

        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/" + employee.getId()))
                .andExpect(status().isNotFound());
    }
}

