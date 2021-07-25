package com.pplflw.usermanagementapi.domain.service;

import com.pplflw.usermanagementapi.domain.Employee;
import com.pplflw.usermanagementapi.domain.EmployeePersonalData;
import com.pplflw.usermanagementapi.domain.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.pplflw.usermanagementapi.domain.EmployeeProvider.getCreatedEmployee;
import static com.pplflw.usermanagementapi.domain.EmployeeProvider.getEmployeePersonalData;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DomainEmployeeServiceUnitTest {

    private EmployeeRepository employeeRepository;
    private DomainEmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new DomainEmployeeService(employeeRepository);
    }

    @Test
    void addEmployee_shouldCreateEmployee_thenSaveIt() {
        final EmployeePersonalData employeePersonalData = getEmployeePersonalData();

        final UUID resultId = employeeService.addEmployee(employeePersonalData);

        verify(employeeRepository).save(any(Employee.class));
        assertNotNull(resultId);
    }

    @Test
    void addEmployee_shouldThrowException_with_alreadyExistingEmployee() {
        final EmployeePersonalData employeePersonalData = getEmployeePersonalData();
        final Employee employee = getCreatedEmployee();
        when(employeeRepository.existsEmployeeByLogin(employee.getLogin())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class,
                () -> employeeService.addEmployee(employeePersonalData));

        String expectedMessage = "Employee with given login already exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void changeEmployeeStatus_shouldChangeStatusThenSaveIt() {
        final Employee employee = spy(getCreatedEmployee());
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        employeeService.changeEmployeeStatus(employee.getId().toString());

        verify(employeeRepository).save(any(Employee.class));
        verify(employee).changeEmployeeStatus(any());
    }

    @Test
    void changeEmployeeStatus_shouldThrowException_with_employeeNotFound() {
        final Employee employee = getCreatedEmployee();
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> employeeService.changeEmployeeStatus(employee.getId().toString()));

        String expectedMessage = "Employee with given id doesn't exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void changeEmployeeStatus_shouldThrowException_with_invalidFormat_Id() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> employeeService.changeEmployeeStatus("invalidId"));

        String expectedMessage = "Invalid UUID string: invalidId";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepository, times(0)).findById(any(UUID.class));
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }
}
