package com.cts.emaily.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.cts.emaily.entity.User;

/**
 * UserService interface for operation on User Database
 */
@Service
public interface UserService {
	
	
	/*
	 * Method to store user details into database
	*/
	public User saveUserToDatabase(@Valid User user, String roleName) ;
	
	/*
	 * Method to view all users of our application
	*/
	public List<User> getAllUserDetails();
	
	/*
	 * Method to load the user details from database with userName
	*/
	public User getUserByName(String userName);
	
	/*
	 * Method to load the user details from database using userId
	*/
	public User getUserById(Integer uID);
	
	/*
	 * Method to check if user is admin or not
	*/
	public Boolean isUserAdmin(String userName);
	
	/*
	 * Method to check if user is Normal user or not
	*/
	public Boolean isUser(String userName);
	
	/*
	 * Method to update user credits
	*/
	public Boolean updateUserCredits(String userName, Integer amount);
	
	/*
	 * Method to update user Balance
	*/
	public Boolean updateUserBalance(String userName, Integer amount);
}
