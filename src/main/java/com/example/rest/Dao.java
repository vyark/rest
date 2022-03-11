package com.example.rest;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(Employee employee);

    void delete(T t);
}