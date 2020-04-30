package com.visitormanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException{
	
	public UsernameAlreadyExistsException(String message)  {
		super(message);
	}
}
