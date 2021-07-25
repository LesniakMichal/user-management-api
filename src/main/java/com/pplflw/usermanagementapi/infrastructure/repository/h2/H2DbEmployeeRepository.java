package com.pplflw.usermanagementapi.infrastructure.repository.h2;

import com.pplflw.usermanagementapi.domain.Employee;
import com.pplflw.usermanagementapi.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class H2DbEmployeeRepository implements EmployeeRepository {
    private final SpringDataH2EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> findById(UUID id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(EmployeeEntity::toEmployee);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(new EmployeeEntity(employee));
    }

    @Override
    public boolean existsEmployeeByLogin(String login) {
        return employeeRepository.existsEmployeeByLogin(login);
    }
}
