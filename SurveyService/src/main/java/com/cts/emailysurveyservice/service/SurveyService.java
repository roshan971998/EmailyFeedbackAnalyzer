package com.cts.emailysurveyservice.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.emailysurveyservice.dto.CreateSurveyRequest;
import com.cts.emailysurveyservice.model.Survey;

/**
 * Service interface for Survey Management
 */

@Service
public interface SurveyService {

	/*
	 * As a User of the Application
	 * 
	 */
	public Survey addSurvey(CreateSurveyRequest createSurveyRequest);

	public List<Survey> getUserSurveys(Integer userId);

	public Survey deleteSurvey(Integer userId, Integer surveyId);

	public Boolean repondToSurvey(Integer userId, Integer surveyId);

	/*
	 * As a Admin of the Application
	 * 
	 */
	public List<Survey> getSurveyByDate(Date date);
	public List<Survey> getAllSurveys();

}
