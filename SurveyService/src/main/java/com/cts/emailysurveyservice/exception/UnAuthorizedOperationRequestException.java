package com.cts.emailysurveyservice.exception;

public class UnAuthorizedOperationRequestException extends RuntimeException {

	/**
	 * This is UnAuthorizedOperationRequest contructor
	 * 
	 * @param message
	 */
	public UnAuthorizedOperationRequestException(String message) {
		super(message);
	}
}
