package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/employees")
    public List<Employee> all() throws Exception {
        return service.hiredEmployees();
    }

    @GetMapping("/salary/{id}")
    public BigDecimal salary(@PathVariable String id) {
        return service.getSalary(Long.valueOf(id));
    }
}