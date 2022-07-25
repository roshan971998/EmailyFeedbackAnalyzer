package com.cts.emailysurveyservice.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

/**
 * Email Service interface for Email Management
 */

@Service
public interface EmailService {
	
	public Boolean sendSimpleEmail(String toEmail, String body, String subject);
	
	public Boolean sendEmailWithAttachment(String toEmail, String body, String subject, String attachment) throws MessagingException ;
	
	public Boolean sendMultipleMail(String from, String[] to, String subject, String msg) throws MessagingException ;
	
}

