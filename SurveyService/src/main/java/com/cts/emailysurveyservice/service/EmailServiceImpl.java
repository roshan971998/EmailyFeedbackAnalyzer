package com.cts.emailysurveyservice.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public Boolean sendSimpleEmail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("rajr4350@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);

		mailSender.send(message);
		System.out.println("Mail Sent successfully...");
		return true;
		
	}
	@Override
	public Boolean sendEmailWithAttachment(String toEmail, String body, String subject, String attachment)
			throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setFrom("spring.email.from@gmail.com");
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);

		FileSystemResource fileSystem = new FileSystemResource(new File(attachment));

		mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);

		mailSender.send(mimeMessage);
		System.out.println("Mails with Attachments Sent Successfully...");
		return true;

	}

	@Override
	public Boolean sendMultipleMail(String from, String[] to, String subject, String msg)	//, List attachments)
			throws MessagingException {

		// Creating message
		MimeMessage mimeMsg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(msg + "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "  <head>\n"
				+ "    <meta charset=\"UTF-8\" />\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
				+ "    <title>Document</title>\n"
				+ "  </head>\n"
				+ "  <body>\n"
				+ "    <h1>To Respond to survey click on the link :</h1>\n"
				+ "    <a href=\"http://localhost:3000/feedback\">click here</a>\n"
				+ "  </body>\n"
				+ "</html>\n"
				+ "", true);
		mailSender.send(mimeMsg);
		System.out.println("Multiple mails send Successfully");
		return true;
	}

}


//@EventListener(ApplicationReadyEvent.class)
//public void triggerMail() throws MessagingException {
//
//	service.sendSimpleEmail("rc882794@gmail.com", "This is Email Body with Attachment...",
//			"This email has attachment");
//
////	service.sendEmailWithAttachment("spring.email.to@gmail.com", "This is Email Body with Attachment...",
////			"This email has attachment", "C:\\Users\\shabb\\Pictures\\c.gif");
//	
//	String[] recvrEmails= {"rajr4350@gmail.com","0105it181089@oriental.ac.in","erroshan4350@gmail.com"};
//	service.sendMultipleMail("rc882794@gmail.com", recvrEmails, "leave letter", "Iam not feeling well");
//}