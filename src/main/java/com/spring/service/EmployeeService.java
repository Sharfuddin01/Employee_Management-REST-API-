package com.spring.service;

import com.spring.dto.EmployeeDto;
import com.spring.dto.EmployeeResponseDto;
import com.spring.entity.Employee;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeService {


    public EmployeeDto add(EmployeeDto employeeDto);
    public String update(EmployeeDto employeeDto,long id);
    public String delete(Long id);
    public EmployeeResponseDto getAllEmployees(int pageNo,int pageSize,String sortBt,String sortDir);
    public EmployeeDto getEmployeeById(String email);
    public String increaseSalary(double percentage);
    public String updateAge(int increaseAge);


}