package com.example.rest;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class Employee {
    private String name;
    private Long id;
    private BigInteger salary;
}
