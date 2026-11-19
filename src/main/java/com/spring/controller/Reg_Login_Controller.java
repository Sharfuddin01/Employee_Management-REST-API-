
package com.spring.controller;

import com.spring.dto.APIResponse;
import com.spring.dto.EmployeeDto;
import com.spring.dto.LoginDto;
import com.spring.service.EmployeeService;
import com.spring.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")
public class Reg_Login_Controller {

	private final EmployeeService employeeService;
	private final AuthenticationManager manager;
	private final JwtService jwtService;

	public Reg_Login_Controller(EmployeeService employeeService, AuthenticationManager manager, JwtService jwtService) {
		this.employeeService = employeeService;
		this.manager = manager;
		this.jwtService = jwtService;
	}

	
	@PostMapping("/add")
	public ResponseEntity<APIResponse<String>> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto,
			BindingResult result) {
		
		APIResponse<String> response = new APIResponse<>();
		 if(result.hasErrors()) {
		   response.setMassege("Something went wrong");
		   response.setStatus(400);
		   response.setData(result.getFieldError().getDefaultMessage());
		   return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		 }
		 EmployeeDto emp = employeeService.add(employeeDto);

		if (emp != null) {
			response.setMassege("Created Successfully");
			response.setStatus(201);
			response.setData("Employee has been saved successfully");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.setMassege("Faild");
			response.setStatus(201);
			response.setData("Employee could not be saved");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	  @PostMapping("/login")
	  public ResponseEntity<APIResponse<String>> loginEmployee(@RequestBody LoginDto  loginDto) {

		APIResponse<String> response = new APIResponse<>();

		  UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
		  try{
			  Authentication authentication = manager.authenticate(token);
			  if(authentication.isAuthenticated()) {
				  String JwtToken=jwtService.generateToken(loginDto.getEmail());
				  response.setData(JwtToken);
				  response.setStatus(200);
				  response.setMassege("Logged in Successfully");
				  return new ResponseEntity<>(response, HttpStatus.OK);
			  }
		  }
		  catch(Exception e){
			  response.setMassege("Faild");
			  response.setStatus(400);
			  response.setData(e.getMessage());
			  return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		  }
		  return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	  }
}
