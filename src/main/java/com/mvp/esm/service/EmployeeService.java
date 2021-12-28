package com.mvp.esm.service;

import com.mvp.esm.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployeeById(String id);

    void deleteEmployeeById(String id);

    void updateEmployee(String id, Employee employee);
    //Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
