package com.cts.emailysurveyservice.exception;

public class RecipientListEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * This is RecipientListFoundEmptyException contructor
	 * 
	 * @param message
	 */
	public RecipientListEmptyException(String message) {
		super(message);
	}
}
