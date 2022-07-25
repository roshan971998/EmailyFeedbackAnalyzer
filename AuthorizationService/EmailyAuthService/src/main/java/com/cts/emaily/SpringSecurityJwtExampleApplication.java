package com.cts.emaily;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.cts.emaily.entity.Role;
import com.cts.emaily.entity.User;
import com.cts.emaily.repository.RoleRepository;
import com.cts.emaily.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/**
 * This is the main class for Authorization service
 * 
 * @authorPOD 4
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition
public class SpringSecurityJwtExampleApplication {
	@Autowired
	private RoleRepository repository;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void initUsers() {
		List<User> users = Stream.of(new User("javatechie", "javatechie@gmail.com", "password", 0),
				new User("rahul", "jatechie@gmail.com", "password", 0),
				new User("rony", "javie@gmail.com", "password", 0),
				new User("sunnty", "echie@gmail.com", "password", 0)).collect(Collectors.toList());
		Role role = new Role(1, "Admin", users);
		repository.save(role);

		List<User> users2 = Stream
				.of(new User("ram", "ram@gmail.com", "password", 0), new User("laxman", "lax@gmail.com", "password", 0))
				.collect(Collectors.toList());
		Role role2 = new Role(2, "User", users2);
		repository.save(role2);

		List<Role> roleList = repository.findAll();
		roleList.forEach(r -> {
			System.out.println(r);
		});

		System.out.println(userRepository.findByUserName("rahul"));
		System.out.println(userRepository.findByUserName("zxvcv"));
//      repository.findByRoleNameAndUsers("Admin",new User("rahul", "jatechie@gmail.com", "password", 0));
	}

	/**
	 * Main Function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtExampleApplication.class, args);
	}

}

//http://localhost:8090/v3/api-docs
//http://localhost:8090/swagger-ui/      ---0ld swagger2 available at
