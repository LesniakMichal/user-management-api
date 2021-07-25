package com.pplflw.usermanagementapi.domain.service;

import com.pplflw.usermanagementapi.domain.Employee;
import com.pplflw.usermanagementapi.domain.EmployeePersonalData;
import com.pplflw.usermanagementapi.domain.EmployeeStatus;
import com.pplflw.usermanagementapi.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DomainEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UUID addEmployee(final EmployeePersonalData employeePersonalData) {
        validateIfEmployeeAlreadyExists(employeePersonalData.getLogin());
        final Employee employee = new Employee(employeePersonalData);
        employeeRepository.save(employee);
        return employee.getId();
    }

    @Override
    public EmployeeStatus changeEmployeeStatus(final String id) {
        final Employee employee = getEmployee(UUID.fromString(id));
        final var changedEmployeeStatus = employee.changeEmployeeStatus(employee.getEmployeeStatus());
        employeeRepository.save(employee);
        return changedEmployeeStatus;
    }

    @Override
    public EmployeeStatus getEmployeeStatus(final String id) {
        final Employee employee = getEmployee(UUID.fromString(id));
        return employee.getEmployeeStatus();
    }

    private Employee getEmployee(final UUID id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with given id doesn't exist."));
    }

    private void validateIfEmployeeAlreadyExists(String login) {
        if (employeeRepository.existsEmployeeByLogin(login)) {
            throw new RuntimeException("Employee with given login already exist.");
        }
    }
}