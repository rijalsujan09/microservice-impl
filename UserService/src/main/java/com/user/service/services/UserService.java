package com.user.service.services;

import java.util.List;

import com.user.service.entities.User;

public interface UserService {

	// User operation
	
	//-> create user
	User saveUser(User user);
	
	//-> get all user
	List<User> getAllUser();
	
	//-> get by id
	User  getUser(String userId);
	//-> get by id
	User  getUserById(String userId);
	
	//-> delete user
	void deleteUser(String userId);
	
	//-> update User
	User updateUser(String userId, User user);
}
