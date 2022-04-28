package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> hiredEmployees() {
        return employeeDao.getAll();
    }

    @Override
    public BigDecimal getSalary(Long hiredEmployeeId) {
        Employee employee = employeeDao.get(hiredEmployeeId).orElseThrow();
        BigDecimal truncatedDouble = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(500, 2000))
                .setScale(2, RoundingMode.HALF_UP);
        employee.setSalary(truncatedDouble);
        return employee.getSalary();
    }
}
