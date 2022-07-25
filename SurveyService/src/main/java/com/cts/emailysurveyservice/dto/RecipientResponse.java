package com.cts.emailysurveyservice.dto;

import com.cts.emailysurveyservice.model.Recipient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Pojo class for recipient response
 * 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model class for recipient response")
public class RecipientResponse {

	@Schema(description = "Email Id of the recipients")
	private String emailId;

	public RecipientResponse(Recipient recipient) {
		this.emailId = recipient.getEmailId();
	}
}
