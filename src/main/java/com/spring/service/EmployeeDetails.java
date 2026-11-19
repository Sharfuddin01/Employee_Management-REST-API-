package com.spring.service;

import com.spring.entity.Employee;
import com.spring.repository.EmployeeRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class EmployeeDetails implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    public EmployeeDetails(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee employee=employeeRepository.findByEmail(email);
        return new User(employee.getEmail(),employee.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+employee.getRole())));
    }
}
