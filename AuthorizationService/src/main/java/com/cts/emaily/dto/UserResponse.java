package com.cts.emaily.dto;

import com.cts.emaily.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO class for user response
 * 
 */

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Model class for representing a user of the application")
public class UserResponse {

	@Schema(description = "User id of the user")
	private Integer userId;
	@Schema(description = "Username of the user")
	private String userName;
	
	@Schema(description = "Email Id of the user")
	private String emailId;
	@Schema(description = "Current credit of the user")
	private Integer credits;
	
	@Schema(description = "Role of the user")
	private String roleName;

	public UserResponse(User user) {
		this.userName = user.getUserName();
		this.userId = user.getId();
		this.emailId = user.getEmailId();
		this.credits = user.getCredits();
	}
}
