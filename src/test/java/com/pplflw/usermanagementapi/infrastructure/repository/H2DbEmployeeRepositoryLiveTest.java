package com.pplflw.usermanagementapi.infrastructure.repository;

import com.pplflw.usermanagementapi.domain.Employee;
import com.pplflw.usermanagementapi.domain.EmployeePersonalData;
import com.pplflw.usermanagementapi.domain.repository.EmployeeRepository;
import com.pplflw.usermanagementapi.infrastructure.repository.h2.SpringDataH2EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class H2DbEmployeeRepositoryLiveTest {

    @Autowired
    private SpringDataH2EmployeeRepository h2EmployeeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void cleanUp() {
        h2EmployeeRepository.deleteAll();
    }

    @Test
    void shouldFindById_thenReturnEmployee() {
        final Employee employee = createEmployee();

        employeeRepository.save(employee);
        final Optional<Employee> result = employeeRepository.findById(employee.getId());

        assertEquals(employee, result.get());
    }

    @Test
    void existsEmployeeByLogin_with_ExistingUser() {
        final Employee employee = createEmployee();
        employeeRepository.save(employee);

        final boolean result = employeeRepository.existsEmployeeByLogin(employee.getLogin());

        assertTrue(result);
    }

    private Employee createEmployee() {
        return new Employee(new EmployeePersonalData("Joe", "Doe", "testLogin"));
    }
}
