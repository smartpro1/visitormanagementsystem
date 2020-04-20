package com.visitormanagement.exceptions;

public class UsernameOrEmailExceptionResponse {
 private String usernameorEmail;

public UsernameOrEmailExceptionResponse(String usernameorEmail) {
	this.usernameorEmail = usernameorEmail;
}

public String getUsernameorEmail() {
	return usernameorEmail;
}

public void setUsernameorEmail(String usernameorEmail) {
	this.usernameorEmail = usernameorEmail;
}
 

 
}
