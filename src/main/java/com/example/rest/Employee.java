package com.example.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private String name;
    private Long id;
    private Double salary;
}
