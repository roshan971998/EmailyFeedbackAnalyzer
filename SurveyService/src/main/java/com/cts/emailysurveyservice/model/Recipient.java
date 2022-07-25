package com.cts.emailysurveyservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Recipient Entity Class
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "recipients")
@Schema(description = "Model class for Recipients of the survey")
public class Recipient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Schema(description = "Id of the recipients")
	private Integer id;

	@Column(name = "email_id")
	@Schema(description = "Email Id of the recipients")
	private String emailId;

	@ColumnDefault("false")
	@Column(name = "responded")
	@Schema(description = "Response of the recipient to the survey")
	private boolean responded;

}
