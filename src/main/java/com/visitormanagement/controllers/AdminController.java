package com.visitormanagement.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visitormanagement.models.Admin;
import com.visitormanagement.payloads.AdminLoginPayload;
import com.visitormanagement.payloads.AdminRequestPayload;
import com.visitormanagement.services.AdminService;
import com.visitormanagement.services.FieldsValidationService;

@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	FieldsValidationService validateFields;
	
	@PostMapping("register")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRequestPayload adminRequest, BindingResult result){
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		Admin newAdmin = adminService.registerAdmin(adminRequest);
		return new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> loginAdmin(AdminLoginPayload adminLoginRequest) {
		
		return null;
	}

}
