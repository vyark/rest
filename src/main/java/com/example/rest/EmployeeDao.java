package com.example.rest;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.*;

@Component
public class EmployeeDao implements Dao<Employee> {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeDao() {
        Employee employee1 = Employee.builder().id(1L).name("Olga").salary(new BigInteger("5000")).build();
        Employee employee2 = Employee.builder().id(1L).name("Tom").salary(new BigInteger("200")).build();
        Employee employee3 = Employee.builder().id(1L).name("Stas").salary(new BigInteger("1300")).build();
        Employee employee4 = Employee.builder().id(1L).name("Pam").salary(new BigInteger("8000")).build();

        employees.addAll(Arrays.asList(employee1, employee2, employee3, employee4));
    }

    @Override
    public Optional<Employee> get(long id) {
        return Optional.ofNullable(employees.get((int) id));
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }

    @Override
    public void save(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void update(Employee employee) {
        int index = employees.indexOf(employee);
        employees.set(index, employee);
    }

    @Override
    public void delete(Employee employee) {
        employees.remove(employee);
    }
}