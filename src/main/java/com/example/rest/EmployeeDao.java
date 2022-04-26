package com.example.rest;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDao implements Dao<Employee> {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeDao() {
        Employee employee1 = Employee.builder().id(0L).name("Olga").salary(500d).build();
        Employee employee2 = Employee.builder().id(1L).name("Tom").salary(2000d).build();
        Employee employee3 = Employee.builder().id(2L).name("Stas").salary(1300d).build();
        Employee employee4 = Employee.builder().id(3L).name("Pam").salary(800d).build();

        employees.addAll(Arrays.asList(employee1, employee2, employee3, employee4));
    }

    @Override
    public Optional<Employee> get(Long id) {
        return Optional.ofNullable(employees.get(id.intValue()));
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }
}