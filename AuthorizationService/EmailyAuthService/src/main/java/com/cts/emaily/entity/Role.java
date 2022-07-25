package com.cts.emaily.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for storing user roles in database
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@Schema(description = "Model class for Roles alllowed in the application")
public class Role {

	@Id
	@Schema(description = "Id of the role")
	private Integer id;

	@Schema(description = "Name of the role")
	@Column(nullable = false,name = "role_name")
	@Length(max=5)
	private String roleName;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private List<User> users = new ArrayList<>();
	
	

	public Role(String roleName, List<User> userList) {
		this.roleName = roleName;
		this.users = userList;
	}
	public Role(String roleName,User user) {
		this.roleName = roleName;
		this.users.add(user);
	}
	public Role(Integer id ,String roleName,User user) {
		this.id =id;
		this.roleName = roleName;
		this.users.add(user);
	}
}
