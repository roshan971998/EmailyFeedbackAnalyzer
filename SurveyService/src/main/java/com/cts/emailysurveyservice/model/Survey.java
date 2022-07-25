package com.cts.emailysurveyservice.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.cts.emailysurveyservice.dto.CreateSurveyRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Survey Entity Class
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "surveys")
@Schema(description = "Model class for Survey")
public class Survey {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Schema(description = "Id of the survey")
	private Integer id;

	@Column(name = "title")
	@Schema(description = "Title of the survey")
	private String title;

	@Column(name = "body")
	@Schema(description = "Body of the survey")
	private String body;

	@Column(name = "subject")
	@Schema(description = "Subject of the survey")
	private String subject;

	// one to many unidirectional mapping
	// default fetch type for OneToMany: LAZY
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "survey_id", referencedColumnName = "id")
	@Schema(description = "Recipients of the survey")
	private Set<Recipient> recipients = new HashSet<>();

	@Column(name = "user_id")
	@Schema(description = "User id of the user who created the survey")
	private Integer userId;

	@Column(name = "date_sent")
	@Schema(description = "Date on which survey created")
	@CreationTimestamp
	private Date dateSent;

	@ColumnDefault("false")
	@Schema(description = "Response on the survey")
	private Boolean responded;

	public Survey(CreateSurveyRequest createSurveyRequest) {

		this.title = createSurveyRequest.getTitle();
		this.body = createSurveyRequest.getBody();
		this.subject = createSurveyRequest.getSubject();
		this.userId = createSurveyRequest.getUserid();
	}
}
