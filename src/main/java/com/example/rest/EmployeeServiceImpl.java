package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> hiredEmployees() {
        return employeeDao.getAll();
    }

    @Override
    public CompletionStage<List<Employee>> getSalary() {
        for (Employee employee : employeeDao.getAll()) {
            employee.setSalary(employee.getSalary().multiply(new BigInteger("2")));
        }
        return null;
    }
}
