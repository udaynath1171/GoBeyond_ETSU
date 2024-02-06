package com.etsu.gobeyondclassroom.service;

import java.util.Set;

import com.etsu.gobeyondclassroom.model.User;

public interface UserService {
	User registerUser(String username, String email, String password, String role, Set<String> technologyNames);

	User getUserByUsername(String username);

	boolean authenticateUser(String username, String email, String password);
}
