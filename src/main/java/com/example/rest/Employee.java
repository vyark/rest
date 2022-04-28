package com.example.rest;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Employee {
    private String name;
    private Long id;
    private BigDecimal salary;
}
