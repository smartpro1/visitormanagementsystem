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
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler
	public final ResponseEntity<Object> handleUsernameOrEmailException(UsernameOrEmailException ex, WebRequest req){
		UsernameOrEmailExceptionResponse exceptionResponse = new UsernameOrEmailExceptionResponse(ex.getMessage());
	    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
