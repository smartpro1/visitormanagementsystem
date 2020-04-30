package com.visitormanagement.exceptions;

public class UnauthorizedAccessResponse {
	
	private String usernameOrEmail;
	private String password;
	
	public UnauthorizedAccessResponse() {
		this.usernameOrEmail = "invalid username or email";
		this.password = "invalid password";
	}


	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}


	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}
