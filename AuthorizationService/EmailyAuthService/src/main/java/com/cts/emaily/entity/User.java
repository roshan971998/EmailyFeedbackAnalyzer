package com.cts.emaily.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.cts.emaily.dto.UserRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Model class for Users of the application")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(description = "Id of the user")
	private Integer id;

	@Column(name = "name")
	@Schema(description = "Name of the user")
	private String userName;

	@Column(name = "email_id")
	@Pattern(regexp = "^(.+)@(.+)$")
	@Schema(description = "Email of the user")
	@Email @Length(min = 5, max = 50)
	private String emailId;

	@Column(name = "password")
	@Schema(description = "Password of the user")
	@Length(min = 5, max = 64)
	private String password;

	@Column(name = "credits")
	@Schema(description = "Credits of the user")
	private Integer credits;
	
	public User(String userName, String emailId, String password, Integer credits) {
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.credits = credits;
	}

	public User(@Valid UserRequest userRequest) {
		this.userName=userRequest.getUserName();
		this.emailId=userRequest.getEmailId();
		this.password = userRequest.getPassword();
	}

}
