package com.cts.emailysurveyservice.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.emailysurveyservice.dto.CreateRecipientRequest;
import com.cts.emailysurveyservice.dto.CreateSurveyRequest;
import com.cts.emailysurveyservice.exception.RecipientListEmptyException;
import com.cts.emailysurveyservice.exception.SurveyNotFoundException;
import com.cts.emailysurveyservice.exception.UnAuthorizedOperationRequestException;
import com.cts.emailysurveyservice.model.Recipient;
import com.cts.emailysurveyservice.model.Survey;
//import com.cts.emailysurveyservice.repo.RecipientRepo;
import com.cts.emailysurveyservice.repo.SurveyRepo;

/**
 * Service Implementation class for SurveyService
 */
@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	SurveyRepo surveyRepo;

	/**
	 * Overriding method to save a survey details to database with userId
	 * 
	 * @param createSurveyRequest
	 * @return This returns the survey saved in database
	 */
	@Override
	public Survey addSurvey(CreateSurveyRequest createSurveyRequest) {
		Survey survey = new Survey(createSurveyRequest);
		Set<Recipient> recipientsSet = new HashSet<>();
		if (createSurveyRequest.getRecipients() != null) {
			for (CreateRecipientRequest createRecipientRequest : createSurveyRequest.getRecipients()) {
				Recipient recipient = new Recipient();
				recipient.setEmailId(createRecipientRequest.getEmailId());
				recipient.setResponded(false);
				recipientsSet.add(recipient);
			}
		} else {
			throw new RecipientListEmptyException("Recipient List is Empty!!");
		}
		survey.setRecipients(recipientsSet);
		survey.setDateSent(new java.sql.Date(System.currentTimeMillis()));
		survey.setResponded(false);
		// sending mail to all recipients
		survey = surveyRepo.save(survey);
		return survey;
	}

	/**
	 * Overriding method to get the survey details of a particular user from
	 * database
	 * 
	 * @param userId
	 * @return This returns list of surveys sent by a user
	 */
	@Override
	public List<Survey> getUserSurveys(Integer userId) {
		List<Survey> surveys = null;
		surveys = surveyRepo.findByUserId(userId);
		return surveys;

	}

	/**
	 * Overriding method to delete a survey from database
	 * 
	 * @param userId ,surveyId
	 * @return This returns survey deleted by a user
	 */
	@Override
	public Survey deleteSurvey(Integer userId, Integer surveyId)
			throws SurveyNotFoundException, UnAuthorizedOperationRequestException {
		Optional<Survey> surveyOpt = surveyRepo.findById(surveyId);
		Survey survey = null;
		if (surveyOpt.isPresent()) {
			survey = surveyOpt.get();
			if (survey.getUserId() == userId) {
				surveyRepo.delete(survey);
			} else
				throw new UnAuthorizedOperationRequestException("You are not allowed to perform this operation");
		}
		if (survey == null)
			throw new SurveyNotFoundException("No such Survey Exist");
		return survey;
	}

	/**
	 * Overriding method to all the survey from database according to date
	 * 
	 * @param date
	 * @return This returns list of surveys sent on a particular date
	 */
	@Override
	public List<Survey> getSurveyByDate(Date date) {
		return surveyRepo.findByDateSent(date);

	}
	/**
	 * Overriding method to respond to a survey
	 * 
	 * @param userId ,surveyId
	 * @return boolean for survey responded or not
	 */
	@Override
	public Boolean repondToSurvey(Integer userId, Integer surveyId) throws SurveyNotFoundException {
		Survey survey = surveyRepo.findByIdAndUserId(surveyId, userId);
		if (survey == null) {
			throw new SurveyNotFoundException("No such Survey Exist");
		}
		Integer rowsModified = surveyRepo.updateResponded(userId, surveyId, true);
		return rowsModified != 0 ? true : false;
	}
	
	/**
	 * Overriding method to all the survey in the database
	 * 
	 * 
	 * @return List of surveys in database
	 */
	@Override
	public List<Survey> getAllSurveys(){
		return surveyRepo.findAll();
	}

}
