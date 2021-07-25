package com.pplflw.usermanagementapi.infrastructure.repository.h2;

import com.pplflw.usermanagementapi.domain.Employee;
import com.pplflw.usermanagementapi.domain.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(min = 2, max = 10, message = "About Me must be between 10 and 200 characters")
    @Column(name = "first_name", nullable = false, length = 10)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name", nullable = false, length = 15)
    private String lastName;

    @NotBlank(message = "Login is mandatory")
    @Column(name = "login", nullable = false, length = 10, unique = true)
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EmployeeStatus employeeStatus;

    public EmployeeEntity(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.login = employee.getLogin();
        this.employeeStatus = employee.getEmployeeStatus();
    }

    public Employee toEmployee() {
        return new Employee(id, firstName, lastName, login, employeeStatus);
    }
}
