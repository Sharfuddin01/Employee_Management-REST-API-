package com.spring.service;

import com.spring.dto.EmployeeDto;
import com.spring.dto.EmployeeResponseDto;
import com.spring.entity.Employee;
import com.spring.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImp(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
           this.employeeRepository = employeeRepository;
           this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EmployeeDto add(EmployeeDto employeeDto) {
       Employee emp= new Employee();
       BeanUtils.copyProperties(employeeDto,emp);

       if(employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new RuntimeException(employeeDto.getEmail() + " email is exist");
        }
       if(employeeRepository.existsByMobile(employeeDto.getMobile())){
           throw new RuntimeException(employeeDto.getMobile()+" mobile exists");
       }
         emp.setPassword(passwordEncoder.encode(emp.getPassword()));
         emp.setRole("EMPLOYEE");
         Employee saveEmployee=employeeRepository.save(emp);
         BeanUtils.copyProperties(saveEmployee,employeeDto);
         return employeeDto;

    }

    @Override
    public String update(EmployeeDto employeeDto,long id) {

        Optional<Employee> op = employeeRepository.findById(id);

        if (op.isPresent()) {
            Employee emp = op.get();
            emp.setSalary(employeeDto.getSalary());
            emp.setAge(employeeDto.getAge());
            employeeRepository.save(emp);
            return "Updated";

        }
        else{

            return "Employee not found";
        }
    }
    
    @Override
    public  String delete(Long id) {

        if(!employeeRepository.existsById(id)){
            throw new RuntimeException("Employee not found");
        }
         employeeRepository.deleteById(id);
        return "Deleted";
    }

    @Override
    public EmployeeDto getEmployeeById(String email) {
        Employee employee = employeeRepository.findByEmail(email);

           if(employee==null){
               throw new RuntimeException("Employee not found");
           }
           else{
               EmployeeDto employeeDto = new EmployeeDto();
               BeanUtils.copyProperties(employee, employeeDto);
               return employeeDto;
           }
    }



    @Override
    public EmployeeResponseDto getAllEmployees(int pageNo ,int pageSize,String sortBy,String sortDir) {
    	
    	
    	Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(Sort.Direction.ASC,sortBy):Sort.by(Sort.Direction.DESC,sortBy);
    	Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
           Page<Employee> page=employeeRepository.findAll(pageable);
         
           List<Employee> emp=page.getContent();
           List<EmployeeDto> dtos = new ArrayList<>();
            for(Employee e:emp){
                EmployeeDto employeeDto=new EmployeeDto();
                BeanUtils.copyProperties(e,employeeDto);
                dtos.add(employeeDto);
            }
            
               EmployeeResponseDto response=new EmployeeResponseDto();
               
               response.setContent(dtos);
               response.setFirstPage(page.isFirst());
               response.setLastPage(page.isLast());
               response.setPageNo(page.getNumber());
               response.setPageSize(page.getSize());
               response.setTotalElement(page.getTotalElements());
               response.setTotalPage(page.getTotalPages());
               
            return response;
    }

    @Override
    public String increaseSalary(double percentage){
        int count=employeeRepository.increaseSalaryByPercent(percentage);
        return count+ " " +"Employee salary Increase";
    }

    @Override
    public String updateAge(int increaseAge){
      int count =employeeRepository.increaseAge(increaseAge);
      return count +" " +"Employee age increase";
    }
}
