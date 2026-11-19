package com.spring.controller;

import com.spring.dto.APIResponse;
import com.spring.dto.EmployeeDto;
import com.spring.dto.EmployeeResponseDto;
import com.spring.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;

    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/all")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> fetchALL(

            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "2", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        EmployeeResponseDto employeeResponseDto = employeeService.getAllEmployees(pageNo, pageSize, sortBy, sortDir);
        APIResponse<EmployeeResponseDto> response = new APIResponse<>();
        response.setMassege("Successfully retrieve all employees");
        response.setStatus(200);
        response.setData(employeeResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse<String>> updateEmployee(@PathVariable long id,
                                                              @RequestBody EmployeeDto employeeDto) {

        String feedBack = employeeService.update(employeeDto, id);

        APIResponse<String> response = new APIResponse<>();

        if (feedBack.equalsIgnoreCase("Updated")) {
            response.setData("Modified");
            response.setMassege("Record Updated::");
            response.setStatus(200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMassege("Sorry Something went Wrong");
            response.setStatus(204);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse<String>> deleteEmployee(@RequestParam Long id) {
        String feedBack = employeeService.delete(id);
        APIResponse<String> response = new APIResponse<>();

        if (feedBack.equalsIgnoreCase("Deleted")) {
            response.setMassege("Deleted Successfully");
            response.setStatus(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMassege("failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/salary")
    public ResponseEntity<APIResponse<String>> salaryIncrease(@RequestParam long percentage) {
        APIResponse<String> response = new APIResponse<>();

        String feedBack = employeeService.increaseSalary(percentage);
        if(feedBack!=null){
            response.setMassege("Employee Salary Increase::");
            response.setStatus(200);
            response.setData(feedBack);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMassege("Sorry Something went Wrong");
        response.setStatus(204);
        response.setData("Try Some Time Later");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/age")
    public ResponseEntity<APIResponse<String>> ageIncrease(@RequestParam int increaseAge ) {
        APIResponse<String> response = new APIResponse<>();

        String feedBack=employeeService.updateAge(increaseAge);
        if(feedBack!=null){
            response.setMassege("Employee Age Increase::");
            response.setStatus(200);
            response.setData(feedBack);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMassege("Sorry Something went Wrong");
        response.setStatus(204);
        response.setData("Try Some Time Later");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
