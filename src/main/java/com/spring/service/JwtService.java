package com.spring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spring.entity.Employee;
import com.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private final static String JWT_SECRET = "jwtSecret";
    private final static long EXPIRATION_TIME = 864_000_000;

    public String generateToken(String email) {

        Employee employee = employeeRepository.findByEmail(email);

        return JWT.create()
                .withClaim("role",employee.getRole())
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public String validateTokenAndRetrieveSubject(String token){

        return JWT.require(Algorithm.HMAC256(JWT_SECRET))
                .build()
                .verify(token)
                .getSubject();
    }

}
