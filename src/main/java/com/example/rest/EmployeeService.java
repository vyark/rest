package com.example.rest;

import java.util.List;

public interface EmployeeService {
    List<Employee> hiredEmployees() throws Exception;

    Double getSalary(Long hiredEmployeeId);
}
