package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.dto.CreateEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.DeleteEmployeeResponseDto;
import com.example.rqchallenge.employees.dto.EmployeeDTO;
import com.example.rqchallenge.employees.exception.GlobalExceptionHandler;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/")
@Log4j2
public class EmployeeController extends GlobalExceptionHandler {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<EmployeeDTO> getAllEmployees() {
        log.info("Get all employees ");
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<CreateEmployeeResponseDto> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        log.info("Creating employee ...");
        return new ResponseEntity<>(employeeService.createEmployee(getEmployeeBuilder(employeeInput)),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteEmployeeResponseDto> deleteEmployeeById(String id) {
        log.info("Delete employee  : {}",id);
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        log.info("Get employee by id  {} ", id);
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    private Employee getEmployeeBuilder(Map<String, Object> employeeInput) {
        Employee employee = new Employee();
        employee.setId(Integer.parseInt(employeeInput.get("id").toString()));
        employee.setEmployeeName(employeeInput.get("name").toString());
        employee.setEmployeeAge(Integer.parseInt(employeeInput.get("age").toString()));
        employee.setSalary(Integer.parseInt(employeeInput.get("salary").toString()));
        return employee;
    }

    @GetMapping("/search/{searchString}")
    ResponseEntity<EmployeeDTO> getEmployeesByNameSearch(@PathVariable String searchString) {
        log.info("Searching employees with search string {}", searchString);
        return new ResponseEntity<>(employeeService.getEmployeesByNameSearch(searchString), HttpStatus.OK);
    }

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees(){
        log.info("Get highest salary employees");
        return new ResponseEntity<>(employeeService.getHighestSalaryOfEmployees(), HttpStatus.OK);
    }

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Get top 10 highest salary earnings employee names");
        return new ResponseEntity<>(employeeService.getTop10HighestEarningEmployeeNames(), HttpStatus.OK);
    }

}
