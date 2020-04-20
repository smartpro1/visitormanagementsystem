package com.visitormanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameOrEmailException extends RuntimeException{
	
	public UsernameOrEmailException(String message)  {
		super(message);
	}

}
