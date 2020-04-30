package com.visitormanagement.exceptions;

@SuppressWarnings("serial")
public class InvalidTagException extends RuntimeException{
	
	public InvalidTagException(String message)  {
		super(message);
	}

}
