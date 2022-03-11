package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/employees")
    public List<Employee> all() throws IOException {
        return service.hiredEmployees();
    }

    @GetMapping("/salary")
    public CompletionStage salary() throws IOException {
        return service.getSalary();
    }
}