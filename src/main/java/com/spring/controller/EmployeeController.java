package com.spring.controller;

import com.spring.dto.APIResponse;
import com.spring.dto.EmployeeDto;
import com.spring.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class EmployeeController {

    private final  EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/profile")
    public ResponseEntity<APIResponse<EmployeeDto>> getEmployee(Authentication authentication) {

        APIResponse<EmployeeDto> response = new APIResponse<>();
        String email = authentication.getName();
        EmployeeDto emp = employeeService.getEmployeeById(email);
        if (emp != null) {
            response.setData(emp);
            response.setStatus(200);
            response.setMassege("Data Fetched");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
       else{
           response.setStatus(404);
           response.setMassege("Employee Not Found");
           return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
