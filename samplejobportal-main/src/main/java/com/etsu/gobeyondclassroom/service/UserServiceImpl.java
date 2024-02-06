package com.etsu.gobeyondclassroom.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.etsu.gobeyondclassroom.model.Technology;
import com.etsu.gobeyondclassroom.model.User;
import com.etsu.gobeyondclassroom.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TechnologyService technologyService;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, TechnologyService technologyService,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.technologyService = technologyService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User registerUser(String username, String email, String password, String role, Set<String> technologyNames) {
		// Check if username or email already exists
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RuntimeException("Username already exists: " + username);
		}

		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already exists: " + email);
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password)); // Store hashed password
		user.setRole(role);

		Set<Technology> technologies = new HashSet<>();
		for (String technologyName : technologyNames) {
			Optional<Technology> technologyOptional = technologyService.findTechnologyByName(technologyName);
			technologyOptional.ifPresent(technologies::add);
		}
		user.setTechnologies(technologies);

		return userRepository.save(user);
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public boolean authenticateUser(String username, String email, String password) {
//		Optional<User> userOptional = userRepository.findByUsername(username);
		Optional<User> userOptional = userRepository.findByEmail(email);
		return userOptional.map(user -> passwordEncoder.matches(password, user.getPassword())).orElse(false);
	}
}
