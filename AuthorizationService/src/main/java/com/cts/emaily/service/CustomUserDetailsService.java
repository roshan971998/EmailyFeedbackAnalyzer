package com.cts.emaily.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.emaily.entity.Role;
import com.cts.emaily.entity.User;
import com.cts.emaily.repository.RoleRepository;
import com.cts.emaily.repository.UserRepository;


/**
 * Service implementation class for UserDetailsService
 */
@Service
public class CustomUserDetailsService implements UserDetailsService,UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	
	/**
	 * Overriding method to load the user details from database with userName
	 * 
	 * @param userName
	 * @return This returns the user name and password
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optUser = userRepository.findByUserName(username);
		if (optUser.isPresent()) {
			User user = optUser.get();
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
	
	/**
	 * @Override Method to store user details into database
	 * 
	 * @param proper user credentials
	 * @return This returns the user stored in database
	 */
	@Override
	public User saveUserToDatabase(@Valid User user, String roleName) {
		user.setCredits(0);
		Role role;
		if (roleName.equalsIgnoreCase("Admin")) {
			List<User> adminList = roleRepository.findByRoleName("Admin").getUsers();
			adminList.add(user);
			role = new Role(1, "Admin", adminList);

		} else {
			List<User> userList = roleRepository.findByRoleName("User").getUsers();
			userList.add(user);
			role = new Role(2, "User", userList);
		}
		role = roleRepository.save(role);
		List<User> savedUser = role.getUsers();
		User requireduser;
		for (User u : savedUser) {
			if (u.getUserName() == user.getUserName())
				return u;
		}
		return null;
	}

	/**
	 * @Override Method to view all users of our application
	 * 
	 * @param nothing
	 * @return This returns the list of users present in the database.
	 */
	@Override
	public List<User> getAllUserDetails() {
		return roleRepository.findByRoleName("User").getUsers();
	}
	
	/**
	 * @Override Method to load the user details from database with userName
	 * 
	 * @param userName
	 * @return This returns the user stored in database
	 */
	@Override
	public User getUserByName(String userName) {
		Optional<User> optional = userRepository.findByUserName(userName);
		if (optional.isPresent()) {
			User user = optional.get();
			return user;
		}
		return null;
	}
	
	/**
	 * @Override Method to load the user details from database using userId
	 * 
	 * @param userName
	 * @return This returns user stored in database
	 */
	@Override
	public User getUserById(Integer uID) {
		Optional<User> optional = userRepository.findById(uID);
		if (optional.isPresent()) {
			User user = optional.get();
			return user;
		}
		return null;
	}
	
	/**
	 * @Override Method to check if user is admin or not
	 * 
	 * @param userName
	 * @return This returns Boolean
	 */
	@Override
	public Boolean isUserAdmin(String userName) {
		AtomicReference<Boolean> flag = new AtomicReference<>(false);
		roleRepository.findByRoleName("Admin").getUsers().stream().forEach(user -> {
			if (user.getUserName().equalsIgnoreCase(userName))
				flag.set(true);
		});
		return flag.get();

	}
	/**
	 * @Override Method to check if user is Normal user or not
	 * 
	 * @param userName
	 * @return This returns Boolean
	 */
	@Override
	public Boolean isUser(String userName) {
		AtomicReference<Boolean> flag = new AtomicReference<>(false);
		roleRepository.findByRoleName("User").getUsers().stream().forEach(user -> {
			if (user.getUserName().equalsIgnoreCase(userName))
				flag.set(true);
		});
		return flag.get();

	}

	/**
	 * @Override Method to update user credits
	 * 
	 * @param userName
	 * @return This returns Boolean
	 */
	@Override
	public Boolean updateUserCredits(String userName, Integer amount) {
		User user = this.getUserByName(userName);
		if(user !=null) {
//			user.setCredits(user.getCredits()+amount);
//			userRepository.save(user);
			Integer balance = user.getCredits()+amount;
			Integer rowsAffected = userRepository.UpdateUserCredit(userName, balance);
			if(rowsAffected ==1) return true; else return false;
		}
		return false;
	}
	
	/**
	 * @Override Method to update user credits
	 * 
	 * @param userName
	 * @return This returns Boolean
	 */
	@Override
	public Boolean updateUserBalance(String userName, Integer amount) {
		User user = this.getUserByName(userName);
		if(user !=null) {
			Integer balance = user.getCredits()-amount;
			Integer rowsAffected = userRepository.UpdateUserCredit(userName, balance);
			if(rowsAffected ==1) return true; else return false;
		}
		return false;
	}
}
