package com.pplflw.usermanagementapi.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

import static com.pplflw.usermanagementapi.domain.EmployeeStatus.*;
import static io.vavr.API.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Employee {
    private UUID id;
    private String firstName;
    private String lastName;
    private String login;
    private EmployeeStatus employeeStatus;

    public Employee(final EmployeePersonalData employeePersonalData) {
        this.id = UUID.randomUUID();
        this.firstName = employeePersonalData.getFirstName();
        this.lastName = employeePersonalData.getLastName();
        this.login = employeePersonalData.getLogin();
        this.employeeStatus = ADDED;
    }

    public EmployeeStatus changeEmployeeStatus(final EmployeeStatus employeeStatus) {
        validateEmployeeStatus(employeeStatus);
        validateState();
       return Match(employeeStatus).of(
                Case($(ADDED), this::toInCheckStatus),
                Case($(IN_CHECK), this::toApprovedStatus),
                Case($(APPROVED), this::toActiveStatus)
        );
    }

    private EmployeeStatus toInCheckStatus() {
        return this.employeeStatus = IN_CHECK;
    }

    private EmployeeStatus toApprovedStatus() {
        return this.employeeStatus = APPROVED;
    }

    private EmployeeStatus toActiveStatus() {
        return this.employeeStatus = ACTIVE;
    }

    private void validateState() {
        if (ACTIVE.equals(employeeStatus)) {
            throw new DomainException("The employee is already active.");
        }
    }

    private void validateEmployeeStatus(EmployeeStatus employeeStatus) {
        if (employeeStatus == null) {
            throw new DomainException("The employee status cannot be null.");
        }
    }
}