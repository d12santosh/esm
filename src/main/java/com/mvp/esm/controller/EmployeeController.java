package com.mvp.esm.controller;

import com.mvp.esm.entity.Employee;
import com.mvp.esm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/esm")
public class EmployeeController {
    final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/users")
    public ResponseEntity<RestResponse> getEmployeeList(){
        List<Employee> employeeById = employeeService.getAllEmployees();
        return ResponseEntity.ok(new RestResponse(employeeById));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id){
        Employee employeeById = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeById);
    }

    @PostMapping(value = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> saveEmployee(@Valid @RequestBody Employee employee){
         employeeService.saveEmployee(employee);
        return ResponseEntity.ok(new RestResponse("Successfully created"));
    }

    @PutMapping(value = "/users/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> updateEmployee(@PathVariable String id, @Valid @RequestBody Employee employee){
         employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(new RestResponse("Successfully updated"));
    }

    @DeleteMapping(value = "/users/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> deleteEmployee(@PathVariable String id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(new RestResponse("Successfully deleted"));
    }


}
