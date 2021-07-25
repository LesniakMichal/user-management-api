package com.pplflw.usermanagementapi.domain.service;

import com.pplflw.usermanagementapi.domain.EmployeePersonalData;
import com.pplflw.usermanagementapi.domain.EmployeeStatus;

import java.util.UUID;

public interface EmployeeService {

    UUID addEmployee(EmployeePersonalData employeePersonalData);

    EmployeeStatus changeEmployeeStatus(String id);

    EmployeeStatus getEmployeeStatus(String id);
}
