package com.pplflw.usermanagementapi.domain;

import org.junit.jupiter.api.Test;

import static com.pplflw.usermanagementapi.domain.EmployeeProvider.getEmployeeWithSpecificStatus;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeUnitTest {

    @Test
    void shouldChangeEmployeeStatus_from_ADDED_to_IN_CHECK() {
        Employee employee = getEmployeeWithSpecificStatus(EmployeeStatus.ADDED);

        employee.changeEmployeeStatus(employee.getEmployeeStatus());

        assertEquals(EmployeeStatus.IN_CHECK, employee.getEmployeeStatus());
    }

    @Test
    void shouldChangeEmployeeStatus_from_IN_CHECK_to_APPROVED() {
        Employee employee = getEmployeeWithSpecificStatus(EmployeeStatus.IN_CHECK);

        employee.changeEmployeeStatus(employee.getEmployeeStatus());

        assertEquals(EmployeeStatus.APPROVED, employee.getEmployeeStatus());
    }

    @Test
    void shouldChangeEmployeeStatus_from_APPROVED_to_ACTIVE() {
        Employee employee = getEmployeeWithSpecificStatus(EmployeeStatus.APPROVED);

        employee.changeEmployeeStatus(employee.getEmployeeStatus());

        assertEquals(EmployeeStatus.ACTIVE, employee.getEmployeeStatus());
    }

    @Test
    void changeEmployeeStatus_shouldThrowException_with_EmployeeStatus_ACTIVE() {
        Employee employee = getEmployeeWithSpecificStatus(EmployeeStatus.ACTIVE);

        Exception exception = assertThrows(DomainException.class,
                () -> employee.changeEmployeeStatus(employee.getEmployeeStatus()));

        String expectedMessage = "The employee is already active.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void changeEmployeeStatus_shouldThrowException_with_EmployeeStatus_NULL() {
        Employee employee = getEmployeeWithSpecificStatus(null);

        Exception exception = assertThrows(DomainException.class,
                () -> employee.changeEmployeeStatus(employee.getEmployeeStatus()));

        String expectedMessage = "The employee status cannot be null.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
