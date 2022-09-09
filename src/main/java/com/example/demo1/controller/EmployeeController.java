/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo1.controller;

import com.example.demo1.exception.ResourceNotFoundException;
import com.example.demo1.model.Employee;
import com.example.demo1.repository.EmployeeRepository;
import java.util.List;

import com.example.demo1.schema.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    // create get all employees api
    
    
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
       return employeeRepository.findAll();
    }
    
    
    
    // create employee
    
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmailId(employeeRequest.getEmailId());
        return employeeRepository.save(employee);
    }
    
    // get employee by id      
 
    @GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}
    
    // update employee
        
      @PutMapping("/employees/{id}")
      public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long employeeId
      , @RequestBody Employee employeeDetails  ) 
            throws ResourceNotFoundException {
          Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
                   employee.setFirstName(employeeDetails.getFirstName());
                   employee.setLastName(employeeDetails.getLastName());
                   employee.setEmailId(employeeDetails.getEmailId());
                   employeeRepository.save(employee);
                   return ResponseEntity.ok().body(employee);                
                   
                    
      }
        
    // delete employee by id
    
     
       @DeleteMapping("/employees/{id}")
      public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long employeeId) 
              throws ResourceNotFoundException {
           employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
                  employeeRepository.deleteById(employeeId);
                   return ResponseEntity.ok().build();   
      }
      
}

