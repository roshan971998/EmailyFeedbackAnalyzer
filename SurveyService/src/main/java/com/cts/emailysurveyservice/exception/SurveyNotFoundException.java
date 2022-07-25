package com.cts.emailysurveyservice.exception;

public class SurveyNotFoundException extends RuntimeException {
	/**
	 * This is SurveyNotFoundException contructor
	 * 
	 * @param message
	 */
	public SurveyNotFoundException(String message) {
		super(message);
	}
}
