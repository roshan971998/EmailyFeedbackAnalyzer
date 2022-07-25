package com.cts.emaily.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * POJO class for Authorization requests
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model class for logging in users")
public class AuthRequest {

	@Schema(description = "login username")
	private String userName;
	@Schema(description = "login password")
	private String password;
}
