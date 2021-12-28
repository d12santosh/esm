package com.mvp.esm.service;

import com.mvp.esm.entity.Employee;
import com.mvp.esm.exception.EmployeeNotFoundException;
import com.mvp.esm.exception.EmployeeValidationException;
import com.mvp.esm.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    protected static final String EMPLOYEE_ID_EXIST_MESSAGE = "Employee ID already exists";
    protected static final String NO_SUCH_EMPLOYEE_MESSAGE = "No such employee";
    protected static final String BAD_INPUT_NOT_SUCH_EMPLOYEE = "Bad input - no such employee";
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        checkIfEmployeeExistById(employee);
        validateEmployeeByLogin(employee);
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        return optional.orElseThrow(() -> new EmployeeNotFoundException(BAD_INPUT_NOT_SUCH_EMPLOYEE));
    }

    @Override
    public void deleteEmployeeById(String id) {
        employeeRepository.deleteById(id);

    }


    @Override
    public void updateEmployee(String id, Employee employee) {
        Employee employeeToUpdate = validateEmployeeExist(id);
        validateEmployeeByLoginAndId(employee, id);
        employeeToUpdate.setLogin(employee.getLogin());
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setSalary(employee.getSalary());
        employeeToUpdate.setStartDate(employee.getStartDate());
        employeeRepository.save(employeeToUpdate);

    }

    private void validateEmployeeByLoginAndId(Employee employee, String id) {
        Employee byLogin = employeeRepository.findByLogin(employee.getLogin());
        if (null != byLogin && !byLogin.getId().equals(id)) {
            throw new EmployeeValidationException("Employee login not unique");
        }
    }

    protected void validateEmployeeByLogin(Employee employee) {
        Employee byLogin = employeeRepository.findByLogin(employee.getLogin());
        if (null != byLogin) {
            throw new EmployeeValidationException("Employee login not unique");
        }
    }


    protected Employee validateEmployeeExist(String id) {
        Optional<Employee> persistedEmployee = employeeRepository.findById(id);
        return persistedEmployee.orElseThrow(() -> new EmployeeValidationException(NO_SUCH_EMPLOYEE_MESSAGE));
    }

    protected void checkIfEmployeeExistById(Employee employee) {
        Optional<Employee> byId = employeeRepository.findById(employee.getId());
        byId.ifPresent(e -> {
            throw new EmployeeValidationException(EMPLOYEE_ID_EXIST_MESSAGE);
        });
    }
}
