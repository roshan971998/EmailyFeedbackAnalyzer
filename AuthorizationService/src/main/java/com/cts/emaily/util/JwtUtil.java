package com.cts.emaily.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class for checking and providing utilities
 */

@Service
public class JwtUtil {
//	private static final long serialVersionUID = -2550185165626007488L;
	private String secret = "javatechie";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	private static final long EXPIRE_DURATION = 2 * 60 * 60 * 1000; // 1 hour

	

	/**
	 * Method to extract user name from token
	 * 
	 * @param token
	 * @return This returns the extracted user name
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	

	/**
	 * Method to extract the expiration time of the token
	 * 
	 * @param token
	 * @return This returns the time of token expiration in milliseconds
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * Method to check whether token has expired or not
	 * 
	 * @param token
	 * @return This return true if token has expired else returns false
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Method to generate token
	 * 
	 * @param username
	 * @return This returns the generated token
	 */
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}
	
	/**
	 * Method to create the token
	 * 
	 * @param claims
	 * @param subject
	 * @return This returns the generated token
	 */
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	/**
	 * Method to validate the token
	 * 
	 * @param token
	 * @param userDetails
	 * @return This returns true if the token is valid else returns false
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * Method to check if a string contains numbers
	 * 
	 * @param strNum
	 * @return This returns true if the string contains numbers else returns false
	 */
	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
