package com.visitormanagement.exceptions;

public class InvalidPhoneNumExceptionResponse {
  private String invalidPhone;

	public InvalidPhoneNumExceptionResponse(String invalidPhone) {
		this.invalidPhone = invalidPhone;
	}
	
	public String getInvalidPhone() {
		return invalidPhone;
	}
	
	public void setInvalidPhone(String invalidPhone) {
		this.invalidPhone = invalidPhone;
	}
  
  
}
