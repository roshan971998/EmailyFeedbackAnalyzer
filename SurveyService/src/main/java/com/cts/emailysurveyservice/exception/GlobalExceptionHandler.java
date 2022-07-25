package com.cts.emailysurveyservice.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

/**
 * This is the Global Exception Handler Used to handle All the other Exception
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This is the override method for Message Not Readable
	 */
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(new ApiErrorResponse(status, "Malformed JSON request", ex), status);
	}

	/**
	 * For Handling RecipientListEmptyFoundException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RecipientListEmptyException.class)
	public ResponseEntity<ApiErrorResponse> handleRecipientListEmptyFoundException(RecipientListEmptyException ex) {
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
		errorResponse.setLocalizedMessage(ex.getLocalizedMessage());
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * For Handling SurveyNotFoundException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(SurveyNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleSurveyNotFoundException(SurveyNotFoundException ex) {
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
		errorResponse.setLocalizedMessage(ex.getLocalizedMessage());
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * For Handling UnAuthorizedOperationRequest
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UnAuthorizedOperationRequestException.class)
	public ResponseEntity<ApiErrorResponse> handleUnAuthorizedOperationRequest(
			UnAuthorizedOperationRequestException ex) {
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
		errorResponse.setLocalizedMessage(ex.getLocalizedMessage());
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * For Handling UserNotFoundException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND);
		errorResponse.setLocalizedMessage(ex.getLocalizedMessage());
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * For handling FeignException
	 * 
	 * @param ex
	 * @param response
	 * @return
	 */
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ApiErrorResponse> handleFeignStatusException(FeignException ex,
			HttpServletResponse response) {
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST);
		errorResponse.setLocalizedMessage(ex.getLocalizedMessage());
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
