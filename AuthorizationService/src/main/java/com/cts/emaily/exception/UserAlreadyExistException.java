package com.cts.emaily.exception;

/**
 * Class for handling UserNotFoundException
 */
public class UserAlreadyExistException extends RuntimeException {
	/**
	 * This method sets the custom error message
	 * 
	 * @param message
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String msg) {
		super(msg);
	}

}
