package com.cts.emaily.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.emaily.dto.UserRequest;
import com.cts.emaily.dto.UserResponse;
import com.cts.emaily.entity.AuthRequest;
import com.cts.emaily.entity.JwtResponse;
import com.cts.emaily.entity.User;
import com.cts.emaily.exception.UserAlreadyExistException;
import com.cts.emaily.exception.UserNameNumericException;
import com.cts.emaily.exception.UserNotFoundException;
import com.cts.emaily.repository.UserRepository;
import com.cts.emaily.service.CustomUserDetailsService;
import com.cts.emaily.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * Class for Authorization Controller
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class AuthorizationController {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	UserRepository repository;

	
	@Operation(summary = "This is to check the health of Authorization controller")
	@ApiResponse(responseCode = "200",description = "Up and running")
	@ApiResponse(responseCode = "403", description = "Request forbidden")
	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}

	/**
	 * Method to validate the token
	 * 
	 * @param token1 This is the token send for authentication
	 * @return This returns true/false based on token validity
	 */
	@Operation(summary = "This is to validate the token", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Token validated successfully",
					content = @Content(mediaType = "text")),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validate(@RequestHeader(name = "Authorization") String token1) {
		String token = token1.substring(7);
		try {
			UserDetails user = customUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
			log.info("users is =" + user);
			if (jwtUtil.validateToken(token, user)) {
				System.out.println("=================Inside Validate==================");
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				log.debug("Token validation Failed!!");
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Method to check whether login credentials are correct or not
	 * 
	 * @param userCredentials user credentials contain user name and password
	 * @return This returns token on successful login else throws exception
	 */
	// http://localhost:8090/api/auth/user/login
	@Operation(summary = "This is to login a user", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "User logged in successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest) throws Exception {

		if (authRequest.getUserName() == null || authRequest.getPassword() == null
				|| authRequest.getUserName().trim().isEmpty() || authRequest.getPassword().trim().isEmpty()) {
			log.debug("Login unsuccessful --> User name or password is empty");
			throw new UserNotFoundException("User name or password cannot be Null or Empty");
		}

		else if (jwtUtil.isNumeric(authRequest.getUserName())) {
			log.debug("Login unsuccessful --> User name is numeric");
			throw new UserNameNumericException("User name is numeric");
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (DisabledException e) {
			log.debug("Login unsuccessful --> User disabled");
			throw new Exception("USER_DISABLED", e);
		} catch (UsernameNotFoundException e) {
			log.debug("Login unsuccessful --> Invalid username");
			throw new RuntimeException("Bad credentials");
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		log.debug("Login successful");
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUserName());
		String token = jwtUtil.generateToken(userDetails.getUsername());
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	/**
	 * Method to register new users to application
	 * 
	 * @param userCredentials user credentials contain user name and password
	 * @return This returns token on successful registration else throws exception
	 */

	// http://localhost:8090/api/auth/user/register
	@Operation(summary = "This is to regsiter a user", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "User registered successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@PostMapping("/register")
	public ResponseEntity<JwtResponse> register(@Valid @RequestBody UserRequest userRequest) {
		log.info("Requested user is =" + userRequest);
		System.out.println(userRequest);
		try {
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userRequest.getUserName());
			if (userDetails != null) {
				log.debug("Registration unsuccessful --> User already exists");
				throw new UserAlreadyExistException("User already exists");
			}
		} catch (UsernameNotFoundException e) {
			e.getMessage().toString();
		} catch (UserAlreadyExistException e) {
			e.getMessage().toString();
		} catch (Exception e) {
			e.getMessage().toString();
		}
		User registeredUser = customUserDetailsService.saveUserToDatabase(new User(userRequest),userRequest.getRoleName());
		String token = jwtUtil.generateToken(registeredUser.getUserName());
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	/**
	 * Method to view a particular user details
	 * 
	 * @param token This is the token send for authentication
	 * @return This returns requested user detail on successful validation
	 */
	// "Bearer eyJhbI1NiJ9.eyJzdWIiOiJxODh9.XRmk9Lzq0_9nPUH"
	@Operation(summary = "This is to get a user details", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "User fetched successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request Invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@GetMapping("/{userName}")
	public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader(name = "Authorization") String token, @PathVariable String userName) {
		ResponseEntity<Boolean> responseEntity = validate(token);
		if (responseEntity.getBody()) {
			User user = customUserDetailsService.getUserByName(userName);
			if(user!=null) {
				log.info("user details fetched successfully!"+user.toString());
				Boolean isAdmin = customUserDetailsService.isUserAdmin(user.getUserName());
				UserResponse userResponse = new UserResponse(user);
				if(isAdmin) userResponse.setRoleName("Admin"); else userResponse.setRoleName("User");
				return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
			}else {
				return new ResponseEntity<UserResponse>(new UserResponse(),HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<UserResponse>(new UserResponse(),HttpStatus.FORBIDDEN);
	}
	
	
	/**
	 * Method to view all the users of the application
	 * 
	 * @param token This is the token send for authentication
	 * @return This returns List of all users on successful validation
	 */
	@Operation(summary = "This is to get a all user details", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Users fetched successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request Invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAlluserDetails(@RequestHeader(name = "Authorization") String token) {
		ResponseEntity<Boolean> responseEntity = validate(token);
		if (responseEntity.getBody()) {
			String validToken = token.substring(7);
			User userInToken = customUserDetailsService.getUserByName(jwtUtil.extractUsername(validToken));
			Boolean isAdmin = customUserDetailsService.isUserAdmin(userInToken.getUserName());
			if(!isAdmin) {
				log.debug("Only Admin can see all user Details");
				return new ResponseEntity<List<UserResponse>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
			}
			List<User> usersList = customUserDetailsService.getAllUserDetails();
			if (usersList!=null && !usersList.isEmpty()) {
				log.info("Total users in the databse is =" + usersList.size());
				List<UserResponse> userResponses = new ArrayList<>();
				for (User user : usersList) {
					UserResponse userResponse = new UserResponse(user);
					userResponse.setRoleName("User");
					userResponses.add(userResponse);
				}
				return new ResponseEntity<List<UserResponse>>(userResponses, HttpStatus.OK);
			} else {
				log.debug("No users Found in the database");
				return new ResponseEntity<List<UserResponse>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
			}
		}
		log.debug("Operation unsuccessful --> Invalid Credential or token");
		return new ResponseEntity<List<UserResponse>>(new ArrayList<>(), HttpStatus.FORBIDDEN);
	}
	
	
	/**
	 * Method to update a user credit 
	 * 
	 * @param token This is the token send for authentication 
	 * @param username of the user
	 * @param amount to be added
	 * @return This returns List of all users on successful validation
	 */
	
	@Operation(summary = "This is to update user credits", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Users credits updated successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request Invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@PutMapping("/{userName}/{amount}")
	public ResponseEntity<Boolean> updateUserCredits(@RequestHeader(name = "Authorization") String token,
			@PathVariable String userName,@PathVariable String amount) {
		ResponseEntity<Boolean> responseEntity = validate(token);
		if (responseEntity.getBody()) {
			Boolean isUpadated = customUserDetailsService.updateUserCredits(userName, Integer.parseInt(amount));
			if(isUpadated == true) return new ResponseEntity<>(true, HttpStatus.OK);
			else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		log.debug("Operation unsuccessful !!");
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}
	
	/**
	 * Method to update a user credit 
	 * 
	 * @param token This is the token send for authentication 
	 * @param username of the user
	 * @param amount to be added
	 * @return This returns List of all users on successful validation
	 */
	
	@Operation(summary = "This is to update user credits", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Users credits updated successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Request Invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@PutMapping("/balance/{userName}/{amount}")
	public ResponseEntity<Boolean> updateUserBalance(@RequestHeader(name = "Authorization") String token,
			@PathVariable String userName,@PathVariable String amount) {
		ResponseEntity<Boolean> responseEntity = validate(token);
		if (responseEntity.getBody()) {
			Boolean isUpadated = customUserDetailsService.updateUserBalance(userName, Integer.parseInt(amount));
			if(isUpadated == true) return new ResponseEntity<>(true, HttpStatus.OK);
			else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		log.debug("Operation unsuccessful !!");
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}
}
