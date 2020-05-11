package com.visitormanagement.exceptions;

public class InvalidTokenExceptionResponse {
	private String token;

	public InvalidTokenExceptionResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
