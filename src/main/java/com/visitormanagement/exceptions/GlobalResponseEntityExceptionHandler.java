package com.visitormanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler
	public final ResponseEntity<Object> handleUsernameOrEmailException(UsernameOrEmailException ex, WebRequest req){
		UsernameOrEmailExceptionResponse exceptionResponse = new UsernameOrEmailExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleInvalidTagException(InvalidTagException ex, WebRequest req){
		InvalidTagExceptionResponse exceptionResponse = new InvalidTagExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest req){
		UsernameAlreadyExistsExceptionResponse exceptionResponse = new UsernameAlreadyExistsExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest req){
		EmailAlreadyExistsExceptionResponse exceptionResponse = new EmailAlreadyExistsExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleInvalidPhoneNumException(InvalidPhoneNumException ex, WebRequest req){
		InvalidPhoneNumExceptionResponse exceptionResponse = new InvalidPhoneNumExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleInvalidDateException(InvalidDateException ex, WebRequest req){
		InvalidDateExceptionResponse exceptionResponse = new InvalidDateExceptionResponse(ex.getMessage(), ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest req){
		InvalidTokenExceptionResponse exceptionResponse = new InvalidTokenExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}


