package com.example.rest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

public interface EmployeeService {
    List<Employee> hiredEmployees() throws IOException;
    CompletionStage getSalary();
}
