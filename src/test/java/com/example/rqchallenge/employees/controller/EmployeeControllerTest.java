package com.example.rqchallenge.employees.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.rqchallenge.employees.dto.CreateEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.DeleteEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.EmployeeDTO;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO mockEmployeeDTO;
    private List<String> mockTopTenEmployeeNames;
    private CreateEmployeeResponseDto mockCreateEmployeeResponseDto;
    private DeleteEmployeeResponseDto mockDeleteEmployeeResponseDto;

    @BeforeEach
    void setUp() {
        // Initialize mock data
        mockEmployeeDTO = new EmployeeDTO();
        mockEmployeeDTO.setData(List.of(new Employee( "John Doe",4000, "image",30, 25)));

        mockCreateEmployeeResponseDto = new CreateEmployeeResponseDto("success", new Employee("John Doe",4000, "image",30, 25),"success");
        mockDeleteEmployeeResponseDto = new DeleteEmployeeResponseDto("success", "successfully! deleted Record");

        mockTopTenEmployeeNames = List.of("John Doe", "Jane Doe");

        // Initialize mock Employee List
        List<Employee> mockEmployeeList = List.of(new Employee("John Doe",4000, "image",30, 25));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.getEmployees()).thenReturn(mockEmployeeDTO);

        mockMvc.perform(get("/v1/employee/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(mockCreateEmployeeResponseDto);

        mockMvc.perform(post("/v1/employee/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("id", 1, "name", "John Doe", "age", 30, "salary", 1000))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        when(employeeService.deleteEmployeeById(any(String.class))).thenReturn(mockDeleteEmployeeResponseDto);

        mockMvc.perform(delete("/v1/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(any(String.class))).thenReturn(mockEmployeeDTO);

        mockMvc.perform(get("/v1/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployeesByNameSearch() throws Exception {
        when(employeeService.getEmployeesByNameSearch(any(String.class))).thenReturn(mockEmployeeDTO);

        mockMvc.perform(get("/v1/employee/search/John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetHighestSalaryOfEmployees() throws Exception {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(1000);

        mockMvc.perform(get("/v1/employee/highestSalary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1000"));
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() throws Exception {
        when(employeeService.getTop10HighestEarningEmployeeNames()).thenReturn(mockTopTenEmployeeNames);

        mockMvc.perform(get("/v1/employee/topTenHighestEarningEmployeeNames")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("John Doe"))
                .andExpect(jsonPath("$[1]").value("Jane Doe"));
    }
}
