package com.pplflw.usermanagementapi.domain.repository;

import com.pplflw.usermanagementapi.domain.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {
    Optional<Employee> findById(UUID id);

    void save(Employee employee);

    boolean existsEmployeeByLogin(String login);
}
