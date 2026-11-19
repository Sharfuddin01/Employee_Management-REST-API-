package com.spring.JwtFilter;

import com.spring.service.EmployeeDetails;
import com.spring.service.EmployeeService;
import com.spring.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class FilterJwt extends OncePerRequestFilter {

    private final EmployeeDetails employeeDetails;
    private final JwtService jwtService;

      FilterJwt(EmployeeDetails employeeDetails, JwtService jwtService) {
          this.employeeDetails=employeeDetails;
          this.jwtService=jwtService;
      }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          String rawToken=request.getHeader("Authorization");

          if (rawToken != null && rawToken.startsWith("Bearer ")) {
              String token=rawToken.substring(7);

             String authToken =jwtService.validateTokenAndRetrieveSubject(token);

             if(authToken!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
                 var subject = employeeDetails.loadUserByUsername(authToken);
                 var userDetails = new UsernamePasswordAuthenticationToken(subject, null, subject.getAuthorities());
                 userDetails.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(userDetails);
             }
          }

          filterChain.doFilter(request,response);
    }
}
