package com.pplflw.usermanagementapi.domain;

import java.util.UUID;

public class EmployeeProvider {
    public static Employee getEmployeeWithSpecificStatus(EmployeeStatus employeeStatus) {
        return new Employee(UUID.randomUUID(), "testEmployee", "test", "testLogin", employeeStatus);
    }

    public static Employee getCreatedEmployee() {
        return new Employee(getEmployeePersonalData());
    }

    public static EmployeePersonalData getEmployeePersonalData() {
        return new EmployeePersonalData("John", "Doe", "testLogin");
    }
}
