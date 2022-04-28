package com.example.rest;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    List<Employee> hiredEmployees() throws Exception;

    BigDecimal getSalary(Long hiredEmployeeId);
}
