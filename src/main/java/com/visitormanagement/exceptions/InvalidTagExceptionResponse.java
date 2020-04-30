package com.visitormanagement.exceptions;

public class InvalidTagExceptionResponse {
	private String visitorTag;

	public InvalidTagExceptionResponse(String visitorTag) {
		this.visitorTag = visitorTag;
	}

	public String getVisitorTag() {
		return visitorTag;
	}

	public void setVisitorTag(String visitorTag) {
		this.visitorTag = visitorTag;
	}
	
	
	
}
