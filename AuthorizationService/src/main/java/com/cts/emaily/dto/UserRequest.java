package com.cts.emaily.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO class for user request
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model class for registering users into our application")
public class UserRequest {

	@NotBlank(message = "User name cannot be empty")
	@Schema(description = "Unique username to be provided at time of registration")
	private String userName;

	@NotBlank(message = "Email id cannot be empty")
	@Schema(description = "EmailId of the user while registering")
	@Pattern(regexp = "^(.+)@(.+)$")
	private String emailId;

	@NotBlank(message = "Password cannot be blank")
	@Schema(description = "Password of the user while registering")
//	@Pattern(regexp = "^[A-Za-z0-9]+$")
	private String password;
	
	@Schema(description = "Role of the user while registering")
	private String roleName;
}
