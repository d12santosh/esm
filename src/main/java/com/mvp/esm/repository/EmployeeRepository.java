package com.mvp.esm.repository;

import com.mvp.esm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByLogin(String login);
}
