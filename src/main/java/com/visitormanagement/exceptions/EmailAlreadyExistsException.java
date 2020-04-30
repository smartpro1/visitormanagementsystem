package com.visitormanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistsException extends RuntimeException{
	
	public EmailAlreadyExistsException(String message)  {
		super(message);
	}
}
