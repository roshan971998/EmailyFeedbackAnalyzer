package com.cts.emailysurveyservice.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cts.emailysurveyservice.model.Recipient;
import com.cts.emailysurveyservice.model.Survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Pojo class for creating mail request
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateMailRequest {

	public CreateMailRequest(Survey survey) {
		this.title = survey.getTitle();
		this.body = survey.getBody();
		this.subject = survey.getSubject();
		if (survey.getRecipients() != null) {
			recipients = new ArrayList<>();
			for (Recipient recipient : survey.getRecipients()) {
				recipients.add(recipient.getEmailId());
			}
		}
	}

	@NotBlank(message = "Title cannot be Empty")
	private String title;

	@NotBlank(message = "Survey Body cannot be Empty")
	private String body;

	@NotBlank(message = "Survey Subject cannot be Empty")
	private String subject;

	private List<String> recipients = new ArrayList<>();
}
