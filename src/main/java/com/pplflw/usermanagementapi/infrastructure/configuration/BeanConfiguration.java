package com.pplflw.usermanagementapi.infrastructure.configuration;

import com.pplflw.usermanagementapi.UserManagementApiApplication;
import com.pplflw.usermanagementapi.domain.repository.EmployeeRepository;
import com.pplflw.usermanagementapi.domain.service.DomainEmployeeService;
import com.pplflw.usermanagementapi.domain.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = UserManagementApiApplication.class)
public class BeanConfiguration {

    @Bean
    EmployeeService employeeService(final EmployeeRepository employeeRepository) {
        return new DomainEmployeeService(employeeRepository);
    }
}
