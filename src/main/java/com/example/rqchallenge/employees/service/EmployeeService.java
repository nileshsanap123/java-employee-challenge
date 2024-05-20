package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.dto.CreateEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.DeleteEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.EmployeeDTO;
import com.example.rqchallenge.employees.model.Employee;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private RestTemplate restTemplate;
    private static final String GET_ALL_EMPLOYEE_URL = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String POST_URL = "https://dummy.restapiexample.com/api/v1/create";
    private static final String DELETE_URL = "https://dummy.restapiexample.com/api/v1/delete/{id}";
    private static final String GET_EMPLOYEE_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/employee/{id}";



    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmployeeDTO getEmployees() {
        return restTemplate.getForObject(GET_ALL_EMPLOYEE_URL, EmployeeDTO.class);
    }

    public CreateEmployeeResponseDto createEmployee(Employee employee) {
        return restTemplate.postForObject(POST_URL, employee, CreateEmployeeResponseDto.class);
    }

    public EmployeeDTO getEmployeeById(String id) {
        return restTemplate.getForObject(GET_EMPLOYEE_BY_ID_URL, EmployeeDTO.class, id);
    }

    public DeleteEmployeeResponseDto deleteEmployeeById(String id) {
        return restTemplate.exchange(
                DELETE_URL,
                HttpMethod.DELETE,
                null,
                DeleteEmployeeResponseDto.class,
                id
        ).getBody();
    }

    public EmployeeDTO getEmployeesByNameSearch(String name) {
        EmployeeDTO response = restTemplate.getForObject(GET_ALL_EMPLOYEE_URL, EmployeeDTO.class);
        if (response != null && response.getData() != null) {
            List<Employee> filteredEmployees = response.getData().stream()
                    .filter(employee -> employee.getEmployeeName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
            response.setData(filteredEmployees);
        }
        return response;
    }

    public int getHighestSalaryOfEmployees() {
        EmployeeDTO response = restTemplate.getForObject(GET_ALL_EMPLOYEE_URL, EmployeeDTO.class);
        if (response != null && response.getData() != null) {
            return response.getData().stream()
                    .mapToInt(Employee::getSalary)
                    .max()
                    .orElse(0);
        }
        return 0;
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        EmployeeDTO response = restTemplate.getForObject(GET_ALL_EMPLOYEE_URL, EmployeeDTO.class);
        if (response != null && response.getData() != null) {
            return response.getData().stream()
                    .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                    .limit(10)
                    .map(Employee::getEmployeeName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
