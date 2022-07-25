package com.cts.emailysurveyservice.feign;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.emailysurveyservice.dto.CreateMailRequest;

///**
// * Proxy interface for mail sending service
// */
//@FeignClient(name = "mail-service", url = "${mail-service:http://localhost:8090}")
//public interface MailClient {
//
//	/**
//	 * Method for sending the mail
//	 * 
//	 * @param token
//	 * @param CreateMailRequest
//	 * @return This returns Success or Failure message in form of String
//	 */
//	@PostMapping("/api/mail/new")
//	public String sendMail(@RequestHeader(name = "Authorization") String token,
//			 @RequestBody CreateMailRequest createMailRequest);
//}
