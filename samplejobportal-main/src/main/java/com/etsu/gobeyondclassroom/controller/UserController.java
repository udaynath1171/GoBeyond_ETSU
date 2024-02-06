package com.etsu.gobeyondclassroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etsu.gobeyondclassroom.dto.UserLoginRequest;
import com.etsu.gobeyondclassroom.dto.UserRegistrationRequest;
import com.etsu.gobeyondclassroom.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
		
		System.out.println("Mani");

		userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword(), request.getRole(),
				request.getTechnologyNames());
		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest request) {
		// Validate the request and handle errors if needed
		boolean authenticated = userService.authenticateUser(request.getUsername(),request.getEmail(), request.getPassword());
		if (authenticated) {
			return new ResponseEntity<>("Login successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		}
	}
	
}
