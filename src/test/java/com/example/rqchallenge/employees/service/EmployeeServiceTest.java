package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.dto.CreateEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.DeleteEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.EmployeeDTO;
import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeDTO mockEmployeeDTO;
    private Employee mockEmployee;
    private CreateEmployeeResponseDto mockCreateEmployeeResponseDto;
    private DeleteEmployeeResponseDto mockDeleteEmployeeResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock data
        mockEmployee = new Employee( "John Doe",4000, "image",30, 25);
        mockEmployeeDTO = new EmployeeDTO();
        mockEmployeeDTO.setData(List.of(mockEmployee));

        mockCreateEmployeeResponseDto = new CreateEmployeeResponseDto("success", mockEmployee);
        mockDeleteEmployeeResponseDto = new DeleteEmployeeResponseDto("success", "successfully! deleted Record");
    }

    @Test
    void testGetEmployees() {
        when(restTemplate.getForObject(anyString(), eq(EmployeeDTO.class))).thenReturn(mockEmployeeDTO);

        EmployeeDTO result = employeeService.getEmployees();

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("John Doe", result.getData().get(0).getEmployeeName());
    }

    @Test
    void testCreateEmployee() {
        when(restTemplate.postForObject(anyString(), any(Employee.class), eq(CreateEmployeeResponseDto.class)))
                .thenReturn(mockCreateEmployeeResponseDto);

        CreateEmployeeResponseDto result = employeeService.createEmployee(mockEmployee);

        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertEquals("John Doe", result.getData().getEmployeeName());
    }

    @Test
    void testGetEmployeeById() {
        when(restTemplate.getForObject(anyString(), eq(EmployeeDTO.class), anyString())).thenReturn(mockEmployeeDTO);

        EmployeeDTO result = employeeService.getEmployeeById("1");

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("John Doe", result.getData().get(0).getEmployeeName());
    }

    @Test
    void testDeleteEmployeeById() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), isNull(), eq(DeleteEmployeeResponseDto.class), anyString()))
                .thenReturn(ResponseEntity.ok(mockDeleteEmployeeResponseDto));

        DeleteEmployeeResponseDto result = employeeService.deleteEmployeeById("1");

        assertNotNull(result);
        assertEquals("success", result.getStatus());
        assertEquals("successfully! deleted Record", result.getMessage());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        when(restTemplate.getForObject(anyString(), eq(EmployeeDTO.class))).thenReturn(mockEmployeeDTO);

        EmployeeDTO result = employeeService.getEmployeesByNameSearch("John");

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        assertEquals("John Doe", result.getData().get(0).getEmployeeName());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(restTemplate.getForObject(anyString(), eq(EmployeeDTO.class))).thenReturn(mockEmployeeDTO);

        int result = employeeService.getHighestSalaryOfEmployees();

        assertEquals(4000, result);
    }

    @Test
    void testGetTop10HighestEarningEmployeeNames() {
        when(restTemplate.getForObject(anyString(), eq(EmployeeDTO.class))).thenReturn(mockEmployeeDTO);

        List<String> result = employeeService.getTop10HighestEarningEmployeeNames();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0));
    }
}
