package com.pplflw.usermanagementapi.infrastructure.repository.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataH2EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    boolean existsEmployeeByLogin(String login);
}
