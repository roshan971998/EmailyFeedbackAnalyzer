package com.cts.emailysurveyservice.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Pojo class for creating survey request
 * 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model class for Survey creation")
public class CreateSurveyRequest {

	@NotBlank(message = "Title cannot be Empty")
	@JsonProperty("Survey_title")
	@Schema(description = "Title of the survey")
	private String title;

	@NotBlank(message = "Survey Body cannot be Empty")
	@JsonProperty("Survey_body")
	@Schema(description = "Body of the survey")
	private String body;

	@NotBlank(message = "Survey Subject cannot be Empty")
	@JsonProperty("Survey_subject")
	@Schema(description = "Subject of the survey")
	private String subject;

	@JsonProperty("Recipients_list")
	@Schema(description = "Recipients of the survey")
	private Set<CreateRecipientRequest> recipients = new HashSet<>();

	@NotNull(message = "User Id cannot be Empty")
	@JsonProperty("Survey_userId")
	@Schema(description = "User Id of the user creating survey")
	private Integer userid;

}
