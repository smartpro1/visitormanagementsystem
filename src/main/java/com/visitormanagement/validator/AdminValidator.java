package com.visitormanagement.validator;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import com.visitormanagement.payloads.AdminRequestPayload;



@Component
public class AdminValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminRequestPayload.class.equals(clazz); // supports the User class
	}

	@Override
	public void validate(Object object, Errors errors) {
		AdminRequestPayload admin = (AdminRequestPayload) object;
		
		if(admin.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be at least 6 characters");
		}
		
		if(!admin.getPassword().equals(admin.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "passwords must match");
		}
		
	}

}
