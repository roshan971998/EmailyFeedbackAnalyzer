package com.cts.emailysurveyservice.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Pojo class for creating recipient request
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Model class for Recipients of the survey")
public class CreateRecipientRequest {

	@JsonProperty("email_id")
	@NotBlank(message = "Email Id cannot be Empty")
	@Schema(description = "Email Id of the recipients")
	private String emailId;

}
