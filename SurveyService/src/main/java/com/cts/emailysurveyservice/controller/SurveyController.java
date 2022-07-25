package com.cts.emailysurveyservice.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.emailysurveyservice.dto.CreateSurveyRequest;
import com.cts.emailysurveyservice.dto.SurveyResponse;
import com.cts.emailysurveyservice.exception.SurveyNotFoundException;
import com.cts.emailysurveyservice.exception.UnAuthorizedOperationRequestException;
import com.cts.emailysurveyservice.exception.UserNotFoundException;
import com.cts.emailysurveyservice.feign.AuthorisationClient;
//import com.cts.emailysurveyservice.feign.MailClient;
import com.cts.emailysurveyservice.model.Survey;
import com.cts.emailysurveyservice.service.EmailService;
import com.cts.emailysurveyservice.service.SurveyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import com.cts.emailysurveyservice.exception.RecipientListEmptyException;
/**
 * Controller class for Survey service
 */
@RestController
@Slf4j
public class SurveyController {

	@Autowired
	AuthorisationClient authorisationClient;
	
	@Autowired
	SurveyService surveyService;
	
	@Autowired
	EmailService emailService;

	@Operation(summary = "This is to check the health of Authorization controller")
	@ApiResponse(responseCode = "200", description = "Up and running")
	@ApiResponse(responseCode = "403", description = "Request forbidden")
	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}

	/**
	 * Save Survey details
	 * 
	 * @param survey
	 * @return ResponseEntity<String>
	 */
	// http://localhost:8090/api/survey/surveys
	@Operation(summary = "This is to save a survey", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Survey saved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@PostMapping("/surveys")
	public ResponseEntity<Map<String, Object>> addSurvey(@RequestHeader(name = "Authorization") String token,
			@Valid @RequestBody CreateSurveyRequest createSurveyRequest) {
		Map<String, Object> mapResponseEntity = new HashMap<>();
		String message = "";
		SurveyResponse surveyResponse = null;
		HttpStatus httpStatus;
		Integer success = 0;
		if (authorisationClient.validate(token)) {
			Survey survey = new Survey();
			Boolean bool =false;
			try {
				List<String> rc=new ArrayList<>();
				createSurveyRequest.getRecipients().stream().forEach(crr->{
					rc.add(crr.getEmailId());
				});
			bool=emailService.sendMultipleMail("rc882794@gmail.com", rc.stream().toArray(String[]::new),createSurveyRequest.getSubject(), createSurveyRequest.getBody());
			survey = surveyService.addSurvey(createSurveyRequest);	
			} catch (MessagingException | RecipientListEmptyException e) {
				log.debug("Failed to save/send survey!!");
				success = 0;
				message = "Survey Not sent,Try Again!";
				surveyResponse = null;
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			
			if (survey != null && bool == true) {
				log.info("Mails sent successfully and survey is =" + survey.toString());
				message = "Survey Saved and Mail sent successfully";
				surveyResponse = new SurveyResponse(survey);
				httpStatus = HttpStatus.OK;
				success = 1;
			} else {
				log.debug("Failed to save survey!!");
				success = 0;
				message = "Survey Not Saved,Try Again!";
				surveyResponse = null;
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} else {
			log.debug("Token validation Failed!!");
			success = 0;
			message = "Unauthorized to perform the action";
			surveyResponse = null;
			httpStatus = HttpStatus.FORBIDDEN;
		}
		mapResponseEntity.put("message", message);
		mapResponseEntity.put("response", surveyResponse);
		mapResponseEntity.put("success", success);
		mapResponseEntity.put("httpStatus", httpStatus);
		return new ResponseEntity<Map<String, Object>>(mapResponseEntity, httpStatus);
	}

	/**
	 * Retrieve all Survey details for a particular user
	 * 
	 * @param survey
	 * @return ResponseEntity<List<SurveyResponse>>
	 * @throws UserNotFoundException
	 */
	// http://localhost:8090/api/survey/surveys/5
	@Operation(summary = "This is to fetch all surveys of a user using user Id", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Surveys fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "400", description = "Request Invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@GetMapping("/surveys/{uID}")
	public ResponseEntity<Map<String, Object>> getUserSurveys(@RequestHeader(name = "Authorization") String token,
			@PathVariable("uID") Integer uID) throws UserNotFoundException {
		Map<String, Object> mapResponseEntity = new HashMap<>();
		String message = "";
		List<SurveyResponse> surveyResponse = null;
		HttpStatus httpStatus;
		Integer success = 0;
		try {
			if (authorisationClient.validate(token)) {
				List<Survey> surveyList = surveyService.getUserSurveys(uID);
				System.out.println(surveyList);
				if (surveyList != null) {
					List<SurveyResponse> responses = new ArrayList<>();
					log.info("Total Surveys found for given user is =" + surveyList.size());
					message = "Survey fetched successfully";
					surveyList.stream().forEach(survey -> {
						responses.add(new SurveyResponse(survey));
					});
					surveyResponse = responses;
					httpStatus = HttpStatus.OK;
					success = 1;
				} else {
					success = 0;
					log.debug("No survey found for the given" + uID + "user !!");
					throw new SurveyNotFoundException("No survey found for user");
				}
			} else {
				log.debug("Token validation Failed!!");
				success = 0;
				message = "Unauthorized to perform the action";
				surveyResponse = null;
				httpStatus = HttpStatus.FORBIDDEN;
			}
		} catch (SurveyNotFoundException e) {
			success = 0;
			message = "No survey found for the given user !!";
			httpStatus = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			success = 0;
			message = "Unknown error Occured";
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
		}
		mapResponseEntity.put("message", message);
		mapResponseEntity.put("response", surveyResponse);
		mapResponseEntity.put("success", success);
		mapResponseEntity.put("httpStatus", httpStatus);
		return new ResponseEntity<Map<String, Object>>(mapResponseEntity, httpStatus);
	}

	/**
	 * delete Survey details
	 * 
	 * @param surveyId
	 * @param userId
	 * @return ResponseEntity<String>
	 * @throws UnAuthorizedOperationRequestException
	 */
	// http://localhost:8090/api/survey/surveys/5/1
	@Operation(summary = "This is to delete the survey of a user", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Survey deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@DeleteMapping("/surveys/{uId}/{sId}")
	public ResponseEntity<Map<String, Object>> removeSurvey(@RequestHeader(name = "Authorization") String token,
			@PathVariable("uId") int userId, @PathVariable("sId") int surveyId)
			throws UnAuthorizedOperationRequestException {
		Map<String, Object> mapResponseEntity = new HashMap<>();
		String message = "";
		SurveyResponse surveyResponse = null;
		HttpStatus httpStatus = HttpStatus.I_AM_A_TEAPOT;
		Integer success = 0;
		if (authorisationClient.validate(token)) {
			Survey survey = null;
			try {
				survey = surveyService.deleteSurvey(userId, surveyId);
				if (survey != null) {
					log.info("Survey deleted successfully");
					message = "Survey deleted successfully";
					surveyResponse = new SurveyResponse(survey);
					httpStatus = HttpStatus.ACCEPTED;
					success = 1;
				}
			} catch (SurveyNotFoundException e) {
				log.info("Survey deletion Unsuccessfull!!");
				success = 0;
				message = "Survey deletion Unsuccessfull";
				httpStatus = HttpStatus.BAD_REQUEST;
			} catch (UnAuthorizedOperationRequestException e) {
				log.info("User is not the author of this survey");
				success = 0;
				message = "User is not the author of this survey";
				httpStatus = HttpStatus.BAD_REQUEST;
			} catch (Exception e) {
				success = 0;
				message = "Unknown error Occured";
				httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}

		} else {
			log.debug("Token validation Failed!!");
			success = 0;
			message = "Unauthorized to perform the action";
			surveyResponse = null;
			httpStatus = HttpStatus.FORBIDDEN;
		}
		mapResponseEntity.put("message", message);
		mapResponseEntity.put("response", surveyResponse);
		mapResponseEntity.put("success", success);
		mapResponseEntity.put("httpStatus", httpStatus);
		return new ResponseEntity<Map<String, Object>>(mapResponseEntity, httpStatus);
	}

	/**
	 * Find all Surveys Sent on particular date
	 * 
	 * @param date
	 * @return ResponseEntity<List<SurveyResponse>>
	 * @throws SurveyNotFoundException
	 */
	// http://localhost:8090/api/survey/surveys/date/2022-07-06
	@Operation(summary = "This is to find surveys based on date", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Survey deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@GetMapping("/surveys/date/{date}")
	public ResponseEntity<Map<String, Object>> findSurveyByDate(@RequestHeader(name = "Authorization") String token,
			@PathVariable String date) {
		Map<String, Object> mapResponseEntity = new HashMap<>();
		String message = "";
		List<SurveyResponse> surveyResponse = null;
		HttpStatus httpStatus;
		Integer success = 0;
		try {
			if (authorisationClient.validate(token)) {
				List<Survey> surveyList = surveyService.getSurveyByDate(Date.valueOf(date));
				List<SurveyResponse> surveyResponseList = new ArrayList<>();
				if (surveyList != null) {
					log.info("Total Surveys found for given user is =" + surveyList.size());
					message = "Survey fetched successfully";
					surveyList.stream().forEach(survey -> {
						surveyResponseList.add(new SurveyResponse(survey));
					});
					surveyResponse = surveyResponseList;
					httpStatus = HttpStatus.OK;
					success = 1;
				} else {
					success = 0;
					log.debug("No surveys found for given date");
					throw new SurveyNotFoundException("No surveys sent on date:" + date);
				}
			} else {
				log.debug("Token validation Failed!!");
				success = 0;
				message = "Unauthorized to perform the action";
				surveyResponse = null;
				httpStatus = HttpStatus.FORBIDDEN;
			}
		} catch (SurveyNotFoundException e) {
			log.info("Survey deletion Unsuccessfull!!");
			success = 0;
			message = "Survey deletion Unsuccessfull";
			httpStatus = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			success = 0;
			message = "Unknown error Occured";
			httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
		}
		mapResponseEntity.put("message", message);
		mapResponseEntity.put("response", surveyResponse);
		mapResponseEntity.put("success", success);
		mapResponseEntity.put("httpStatus", httpStatus);
		return new ResponseEntity<Map<String, Object>>(mapResponseEntity, httpStatus);
	}

	/**
	 * Respond to a Survey
	 * 
	 * @param date
	 * @return ResponseEntity<List<SurveyResponse>>
	 * @throws SurveyNotFoundException
	 */

	@Operation(summary = "This is to respond to a survey", description = "Multiple status values can be provided with comma seperated strings", responses = {
			@ApiResponse(responseCode = "200", description = "Survey deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "400", description = "Request invalid"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@PutMapping("/surveys/{uId}/{sId}")
	public ResponseEntity<Map<String, Object>> updateSurvey(@RequestHeader(name = "Authorization") String token,
			@PathVariable("uId") int userId, @PathVariable("sId") int surveyId) {
		Map<String, Object> mapResponseEntity = new HashMap<>();
		String message = "";
		HttpStatus httpStatus = HttpStatus.I_AM_A_TEAPOT;
		Integer success = 0;
		try {
			if (authorisationClient.validate(token)) {
				Boolean surveyResponded = surveyService.repondToSurvey(userId, surveyId);
				if (surveyResponded == true) {
					log.info("Survey responed");
					message = "Survey responed";
					httpStatus = HttpStatus.OK;
					success = 1;
				} else {
					log.info("Unable to respond to survey");
					message = "Unable to respond to survey";
					httpStatus = HttpStatus.BAD_REQUEST;
					success = 0;
				}
			} else {
				log.debug("Token validation Failed!!");
				message = "Unauthorized to perform the action";
				success = 0;
				httpStatus = HttpStatus.FORBIDDEN;
			}
		} catch (SurveyNotFoundException e) {
			log.info("Survey deletion Unsuccessfull!!");
			message = "Survey deletion Unsuccessfull";
			success = 0;
			httpStatus = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			success = 0;
			message = "Unknown error Occured";
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		mapResponseEntity.put("message", message);
		mapResponseEntity.put("success", success);
		mapResponseEntity.put("httpStatus", httpStatus);
		return new ResponseEntity<Map<String, Object>>(mapResponseEntity, httpStatus);
	}

	@GetMapping("/surveys")
	public ResponseEntity<Map<String, Object>> getAllUserSurveys(@RequestHeader(name = "Authorization") String token) {
		Map<String, Object> mapResponseEntity = new HashMap<>();
		String message = "";
		List<SurveyResponse> surveyResponse = null;
		HttpStatus httpStatus = HttpStatus.I_AM_A_TEAPOT;
		Integer success = 0;
		try {
			if (authorisationClient.validate(token)) {
				List<Survey> surveyList = surveyService.getAllSurveys();
				if (!surveyList.isEmpty()) {
					List<SurveyResponse> responses = new ArrayList<>();
					log.info("Total Surveys found for given user is =" + surveyList.size());
					message = "Survey fetched successfully";
					surveyList.stream().forEach(survey -> {
						responses.add(new SurveyResponse(survey));
					});
					surveyResponse = responses;
					httpStatus = HttpStatus.OK;
					success = 1;
				} else {
					success = 0;
					log.debug("Databse is empty,No survey found!!");
				}
			} else {
				log.debug("Token validation Failed!!");
				success = 0;
				message = "Unauthorized to perform the action";
				surveyResponse = null;
				httpStatus = HttpStatus.FORBIDDEN;
			}
		} catch (Exception e) {
			success = 0;
			message = "Unknown error Occured";
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		mapResponseEntity.put("message", message);
		mapResponseEntity.put("success", success);
		mapResponseEntity.put("response", surveyResponse);
		mapResponseEntity.put("httpStatus", httpStatus);
		return new ResponseEntity<Map<String, Object>>(mapResponseEntity, httpStatus);
	}
}
