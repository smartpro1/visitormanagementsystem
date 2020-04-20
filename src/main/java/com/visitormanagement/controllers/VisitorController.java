package com.visitormanagement.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visitormanagement.payloads.VisitorRequestPayload;
import com.visitormanagement.services.FieldsValidationService;
import com.visitormanagement.services.VisitorService;

@RestController
@RequestMapping("/api/v1/visitors/")
public class VisitorController {
	
	@Autowired
	FieldsValidationService validateFields;
	
	@Autowired 
	VisitorService visitorService;

	@PostMapping("register-visitor")
	public ResponseEntity<?> registerVisitor(@Valid @RequestBody VisitorRequestPayload visitorRequest, BindingResult result){
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		
		String tag = visitorService.registerVisitor(visitorRequest);

		return new ResponseEntity<String>("Visitor's tag is " + tag, HttpStatus.CREATED);
	}
	
	@PostMapping("logout/{visitorTag}")
	public ResponseEntity<?> signOutVisitor(@PathVariable String visitorTag){
		boolean isValidTag = visitorService.signOutVisitor(visitorTag);
		if(isValidTag) {
			return new ResponseEntity<String>("visitor successfully logged out", HttpStatus.OK);
		}
		return new ResponseEntity<String>("log out declined: invalid tag or visitor", HttpStatus.BAD_REQUEST);
	}
}
