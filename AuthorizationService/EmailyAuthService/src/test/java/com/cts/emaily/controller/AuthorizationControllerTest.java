package com.cts.emaily.controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
/**
 * Test - AuthController class
 */
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.emaily.dto.UserRequest;
import com.cts.emaily.dto.UserResponse;
import com.cts.emaily.entity.AuthRequest;
import com.cts.emaily.entity.JwtResponse;
import com.cts.emaily.repository.RoleRepository;
import com.cts.emaily.repository.UserRepository;
import com.cts.emaily.service.CustomUserDetailsService;
import com.cts.emaily.util.JwtUtil;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorizationControllerTest {
	
	@InjectMocks
	AuthorizationController authorizationController;
	
	UserDetails userdetails;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	JwtUtil jwtUtil;
	
	AuthenticationManager authenticationManager;
	
	@MockBean
	CustomUserDetailsService customUserDetailsService;
	
	@MockBean
	UserRepository userRepository;
	@MockBean
	RoleRepository roleRepository;
	
	@Mock UserRequest userRequest;
	@Mock UserResponse userResponse;
	@Mock AuthRequest authRequest;
	@Mock JwtResponse jwtResponse;
	
	@Test
	void testHealthCheck() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/health-check").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200,result.getResponse().getStatus());
	}
	
	
	@Test
	void validLoginTest() throws Exception { 
		AuthRequest authRequest = new AuthRequest("Rahul","password");
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		UserDetails value = new User(authRequest.getUserName(), authRequest.getPassword(), new ArrayList<>());
		when(customUserDetailsService.loadUserByUsername("Rahul")).thenReturn(value);
		when(jwtUtil.generateToken(authRequest.getUserName())).thenReturn("token");
		ResponseEntity<JwtResponse> login = authorizationController.login(authRequest);
		assertEquals("token", login.getBody().getToken());
	}
	
	
}
