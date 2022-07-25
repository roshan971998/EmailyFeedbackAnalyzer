package com.cts.emailysurveyservice.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.cts.emailysurveyservice.model.Recipient;
import com.cts.emailysurveyservice.model.Survey;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO class for Survey response
 * 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model class for survey response")
public class SurveyResponse {

	@Schema(description = "Id of the survey")
	private Integer id;

	@JsonProperty("Survey_title")
	@Schema(description = "Title of the survey")
	private String title;

	@JsonProperty("Survey_body")
	@Schema(description = "Body of the survey")
	private String body;

	@JsonProperty("Survey_subject")
	@Schema(description = "Subject of the survey")
	private String subject;

	@JsonProperty("Recipients_list")
	@Schema(description = "Recipients of the survey")
	private Set<RecipientResponse> recipients = new HashSet<>();

	@JsonProperty("Survey_userId")
	@Schema(description = "User id of the user")
	private Integer userid;

	@JsonProperty("date_sent")
	@Schema(description = "Date on which survey created")
	private Date dateSent;

	@JsonProperty("responded")
	@Schema(description = "Response on the survey")
	private Boolean responded;

	public SurveyResponse(Survey survey) {
		this.id = survey.getId();
		this.title = survey.getTitle();
		this.body = survey.getBody();
		this.subject = survey.getSubject();
		this.userid = survey.getUserId();
		if (survey.getRecipients() != null) {
			this.recipients = new HashSet<RecipientResponse>();
			for (Recipient recipient : survey.getRecipients()) {
				recipients.add(new RecipientResponse(recipient));
			}
		}
		this.responded = survey.getResponded();
		this.dateSent = survey.getDateSent();
	}

}
