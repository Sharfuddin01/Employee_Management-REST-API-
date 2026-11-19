package com.spring.repository;

import com.spring.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    Employee findByMobile(String mobile);
    Employee findByEmail(String email);

    @Modifying
    @Transactional
    @Query("Update Employee e SET e.salary=e.salary+(e.salary*:percentage/100) WHERE e.role='EMPLOYEE'")
    int increaseSalaryByPercent(@Param("percentage")  double percentage);

    @Modifying
    @Transactional
    @Query("Update Employee e SET e.age=(e.age+:increaseAge)")
       int increaseAge(@Param("increaseAge")   int increaseAge);
}