package com.mvp.esm.service;

import com.mvp.esm.entity.Employee;
import com.mvp.esm.exception.EmployeeNotFoundException;
import com.mvp.esm.exception.EmployeeValidationException;
import com.mvp.esm.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mvp.esm.service.EmployeeServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplTest {

    private static final String DUMMY_ID = "DUMMY_ID";
    private static final String DUMMY_LOGIN = "DUMMY_LOGIN";
    private static final Double DUMMY_SALARY = 10d;
    private static final String DUMMY_NAME = "DUMMY_NAME";

    private static final Employee DUMMY_EMPLOYEE = getDummyEmployee();
    @SpyBean(reset = MockReset.AFTER)
    EmployeeRepository mockRepository;
    
    @Autowired
    EmployeeServiceImpl serviceToTest;

    @Test
    void getAllEmployees() {
        doReturn(Collections.singletonList(DUMMY_EMPLOYEE)).when(mockRepository).findAll();
        List<Employee> allEmployees = serviceToTest.getAllEmployees();
        assertEquals(1, allEmployees.size());
    }

    @Test
    void saveEmployee() {
        doReturn(Optional.empty()).when(mockRepository).findById(DUMMY_EMPLOYEE.getId());
        doReturn(null).when(mockRepository).findByLogin(DUMMY_EMPLOYEE.getLogin());
        doReturn(DUMMY_EMPLOYEE).when(mockRepository).save(DUMMY_EMPLOYEE);
        serviceToTest.saveEmployee(DUMMY_EMPLOYEE);
        verify(mockRepository, times(1)).save(DUMMY_EMPLOYEE);

    }

    @Test
    void getEmployeeById() {
        doReturn(Optional.of(DUMMY_EMPLOYEE)).when(mockRepository).findById(DUMMY_EMPLOYEE.getId());
        Employee employeeById = serviceToTest.getEmployeeById(DUMMY_EMPLOYEE.getId());
        assertEquals(DUMMY_EMPLOYEE.getId(), employeeById.getId());

    }

    @Test
    void getEmployeeById_With_Invalid_ID() {
        doReturn(Optional.empty()).when(mockRepository).findById(DUMMY_EMPLOYEE.getId());
        EmployeeNotFoundException employeeNotFoundException = assertThrows(EmployeeNotFoundException.class, () -> serviceToTest.getEmployeeById(DUMMY_EMPLOYEE.getId()));
        verify(mockRepository, times(1)).findById(DUMMY_EMPLOYEE.getId());
        assertEquals(BAD_INPUT_NOT_SUCH_EMPLOYEE, employeeNotFoundException.getMessage());

    }

    @Test
    void deleteEmployeeById() {
        doNothing().when(mockRepository).deleteById(DUMMY_ID);
        serviceToTest.deleteEmployeeById(DUMMY_ID);
        verify(mockRepository, times(1)).deleteById(DUMMY_ID);
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void validateEmployeeByLogin() {
    }

    @Test
    void validateEmployeeExist_When_Not_Exist() {
        doReturn(Optional.empty()).when(mockRepository).findById(DUMMY_ID);
        EmployeeValidationException employeeValidationException = assertThrows(EmployeeValidationException.class, () -> serviceToTest.validateEmployeeExist(DUMMY_ID));
        Assertions.assertEquals(NO_SUCH_EMPLOYEE_MESSAGE, employeeValidationException.getMessage());
    }

    @Test
    void validateEmployeeExist_When_Exist() {
        doReturn(Optional.of(DUMMY_EMPLOYEE)).when(mockRepository).findById(DUMMY_EMPLOYEE.getId());
        assertDoesNotThrow(() -> serviceToTest.validateEmployeeExist(DUMMY_ID));


    }

    @Test
    protected void checkIfEmployeeExistById_When_Id_Exist() {
        doReturn(Optional.of(DUMMY_EMPLOYEE)).when(mockRepository).findById(DUMMY_EMPLOYEE.getId());
        EmployeeValidationException employeeValidationException = assertThrows(EmployeeValidationException.class, () -> serviceToTest.checkIfEmployeeExistById(DUMMY_EMPLOYEE));
        Assertions.assertEquals(EMPLOYEE_ID_EXIST_MESSAGE, employeeValidationException.getMessage());
    }

    @Test
    protected void checkIfEmployeeExistById_When_Id_Does_Not_Exist() {
        doReturn(Optional.empty()).when(mockRepository).findById(DUMMY_EMPLOYEE.getId());
        assertDoesNotThrow( () -> serviceToTest.checkIfEmployeeExistById(DUMMY_EMPLOYEE));
    }

    private static Employee getDummyEmployee() {
        Employee dummyEmployee = new Employee();
        dummyEmployee.setId(DUMMY_ID);
        dummyEmployee.setLogin(DUMMY_LOGIN);
        dummyEmployee.setName(DUMMY_NAME);
        dummyEmployee.setSalary(DUMMY_SALARY);
        dummyEmployee.setStartDate(LocalDate.now());
        return dummyEmployee;
    }
}