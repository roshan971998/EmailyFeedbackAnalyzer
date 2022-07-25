package com.cts.emaily.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * POJO class for Authorization response
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Model class for returning token on succussfull authentication")
public class JwtResponse {

	@Schema(description = "Authentication token")
	private String token;
}
